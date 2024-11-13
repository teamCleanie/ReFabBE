package cleanie.repatch.post.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.photo.component.PostPhotoManager;
import cleanie.repatch.post.component.PostConverter;
import cleanie.repatch.post.component.PostValidator;
import cleanie.repatch.post.domain.PostEntity;
import cleanie.repatch.post.model.response.PostIdResponse;
import cleanie.repatch.post.model.request.PostRequest;
import cleanie.repatch.post.model.response.PostResponse;
import cleanie.repatch.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostPhotoManager postPhotoManager;
    private final PostConverter postConverter;
    private final PostValidator postValidator;

    @Transactional
    public PostResponse viewPost(Long postId){
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.INVALID_REQUEST));

        return postConverter.toPostResponse(post);
    }

    @Transactional
    public PostIdResponse savePost(PostRequest request, boolean isPublished){
        // 게시글 작성일 때만 검증
        if (isPublished){
            postValidator.validatePostRequest(request);
        }

        // 임시저장이면 검증 건너뛰기
        PostEntity post = postRepository.save(postConverter.toPostEntity(request, isPublished));
        List<PhotoEntity> photos = post.getPhotos();
        postPhotoManager.addPostIdToPhotoEntities(photos, post.getId());

        return postConverter.toPostIdResponse(post.getId());
    }

    @Transactional
    public PostIdResponse updatePost(PostRequest request, Long postId, boolean isPublished){
        PostEntity originalPost = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.INVALID_REQUEST));

        List<PhotoEntity> updatedPhotos = postPhotoManager.updatePostIds(originalPost.getId(), request);
        PostEntity editedPost = postConverter.toEditedPostEntity(originalPost, request, isPublished, updatedPhotos);
        PostEntity updatedPost = postRepository.save(editedPost);

        return postConverter.toPostIdResponse(updatedPost.getId());
    }

    @Transactional
    public boolean deletePostById(Long postId) {

        return postRepository.findById(postId)
                .map(post -> { postRepository.delete(post);
                    return true;
                }).orElse(false);
    }
}
