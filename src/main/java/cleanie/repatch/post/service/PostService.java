package cleanie.repatch.post.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.draft.domain.DraftPost;
import cleanie.repatch.photo.domain.PostPhoto;
import cleanie.repatch.photo.component.PhotosManager;
import cleanie.repatch.photo.domain.PostPhotos;
import cleanie.repatch.post.component.DraftPostManager;
import cleanie.repatch.post.domain.Post;
import cleanie.repatch.post.model.response.PostIdResponse;
import cleanie.repatch.post.model.request.PostRequest;
import cleanie.repatch.post.model.response.PostResponse;
import cleanie.repatch.post.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PhotosManager photosManager;
    private final DraftPostManager draftPostManager;

    @Transactional(readOnly = true)
    public PostResponse viewPost(Long postId){
        Post post = findPostOrThrowException(postId);

        return post.toPostResponse();
    }

    @Transactional(readOnly = true)
    public Post findPostOrThrowException(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND));
    }

    @Transactional
    public PostIdResponse savePost(PostRequest request){
        List<PostPhoto> postPhotoList = photosManager.getPhotoListFromIds(request.photoIds());
        Post savedPost = postRepository.save(Post.toPost(request));

        photosManager.addPostIdToPhotoList(postPhotoList, savedPost.getId());

        return savedPost.toPostIdResponse();
    }

    @Transactional
    public PostIdResponse publishPostFromDraft(Long draftId, PostRequest request) {
        Post post = postRepository.save(Post.toPost(request));
        DraftPost draftPost = draftPostManager.findDraftById(draftId);

        movePhotosFromDraftToPost(draftPost, post);

        return post.toPostIdResponse();
    }

    public void movePhotosFromDraftToPost(DraftPost draftPost, Post post) {
        List<Long> photoIds = draftPost.getDraftPhotos().getPostPhotoList().stream()
                .map(PostPhoto::getId).toList();

        if (!photoIds.isEmpty()) {
            List<PostPhoto> postPhotos = photosManager.getPhotoListFromIds(photoIds);
            photosManager.removeDraftIdFromPhotoList(postPhotos);
            photosManager.addPostIdToPhotoList(postPhotos, post.getId());
        }
    }

    @Transactional
    public PostIdResponse updatePost(PostRequest request, Long postId){
        Post post = findPostOrThrowException(postId);

        List<PostPhoto> updatedPostPhotos = photosManager.updatePostIds(post.getId(), request.photoIds());
        PostPhotos postPhotos = new PostPhotos(updatedPostPhotos);

        post.updatePost(request, postPhotos);

        return post.toPostIdResponse();
    }

    @Transactional
    public void deletePostById(Long postId) {
        postRepository.findById(postId).ifPresentOrElse(
                postRepository::delete,
                () -> {
                    throw new BadRequestException(ExceptionCode.NOT_FOUND);
                }
        );
    }
}
