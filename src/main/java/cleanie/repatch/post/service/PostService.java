package cleanie.repatch.post.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.draft.domain.Draft;
import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.photo.component.PhotosManager;
import cleanie.repatch.photo.domain.Photos;
import cleanie.repatch.post.component.DraftPostManager;
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
    private final PhotosManager photosManager;
    private final DraftPostManager draftPostManager;

    @Transactional
    public PostResponse viewPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.POST_NOT_FOUND));

        return post.toPostResponse(post);
    }

    @Transactional
    public PostIdResponse savePost(PostRequest request){
        List<Photo> photoList = photosManager.getPhotoListFromIds(request.photoIds());
        Post savedPost = postRepository.save(Post.toPost(request));

        photosManager.addPostIdToPhotoList(photoList, savedPost.getId());

        return savedPost.toPostIdResponse(savedPost);
    }

    @Transactional
    public PostIdResponse publishPostFromDraft(Long draftId, PostRequest request) {
        Post post = postRepository.save(Post.toPost(request));
        Draft draft = draftPostManager.findDraftById(draftId);

        Post postWithPhoto = movePhotosFromDraftToPost(draft, post);

        return post.toPostIdResponse(postWithPhoto);
    }

    public Post movePhotosFromDraftToPost(Draft draft, Post post) {
        List<Long> photoIds = draft.getDraftPhotos().getPhotoList().stream()
                .map(Photo::getId).toList();

        if (!photoIds.isEmpty()) {
            List<Photo> photos = photosManager.getPhotoListFromIds(photoIds);
            photosManager.removeDraftIdFromPhotoList(photos);
            photosManager.addPostIdToPhotoList(photos, post.getId());
        }
        return post;
    }

    @Transactional
    public PostIdResponse updatePost(PostRequest request, Long postId){
        Post originalPost = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.POST_NOT_FOUND));

        List<Photo> updatedPhotos = photosManager.updatePostIds(originalPost.getId(), request.photoIds());
        Photos photos = new Photos(updatedPhotos);

        Post editedPost = originalPost.updatePost(originalPost, request, photos);

        return editedPost.toPostIdResponse(editedPost);
    }

    @Transactional
    public boolean deletePostById(Long postId) {

        return postRepository.findById(postId)
                .map(post -> { postRepository.delete(post);
                    return true;
                }).orElse(false);
    }
}
