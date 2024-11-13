package cleanie.repatch.post.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.photo.component.PostPhotoManager;
import cleanie.repatch.post.component.PostConverter;
import cleanie.repatch.post.component.PostValidator;
import cleanie.repatch.post.domain.Post;
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
        Post post = postRepository.findById(postId).orElseThrow(
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
        Post post = postRepository.save(postConverter.toPostEntity(request, isPublished));
        List<Photo> photos = post.getPhotos();
        postPhotoManager.addPostIdToPhotoEntities(photos, post.getId());

        return postConverter.toPostIdResponse(post.getId());
    }

    @Transactional
    public PostIdResponse updatePost(PostRequest request, Long postId, boolean isPublished){
        Post originalPost = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.INVALID_REQUEST));

        List<Photo> updatedPhotos = postPhotoManager.updatePostIds(originalPost.getId(), request);
        Post editedPost = postConverter.toEditedPostEntity(originalPost, request, isPublished, updatedPhotos);
        Post updatedPost = postRepository.save(editedPost);

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
