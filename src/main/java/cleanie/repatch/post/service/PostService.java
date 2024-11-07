package cleanie.repatch.post.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.photo.service.PostPhotoManager;
import cleanie.repatch.post.domain.PostEntity;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.TransactionTypes;
import cleanie.repatch.post.model.PostRequest;
import cleanie.repatch.post.model.PostResponse;
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

    public PostResponse viewSinglePost(Long postId){
        PostEntity post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.INVALID_REQUEST));

        return postConverter.toPostResponse(post);
    }

    @Transactional
    public PostResponse savePost(PostRequest request, boolean isPublished){
        // 게시글 작성일 때만 검증
        if (isPublished){
            postValidator.validatePostRequest(request);
        }

        // 임시저장이면 검증 건너뛰기
        PostEntity post = postRepository.save(postConverter.toPostEntity(request, isPublished));
        List<PhotoEntity> photos = post.getPhotos();
        postPhotoManager.addPostIdToPhotoEntities(photos, post.getId());
        return postConverter.toPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(PostRequest request, Long postId, boolean isPublished){
        PostEntity originalPost = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.INVALID_REQUEST));

        List<PhotoEntity> photos = postPhotoManager.getPhotoEntitiesFromIds(request.photoIds());
        List<PhotoEntity> updatedPhotos = photos.stream()
                .filter(photo -> photo.getPostId() == null)
                .map(photo -> postPhotoManager.addPostIdToPhotoEntity(photo, originalPost.getId()))
                .toList();

        PostEntity editedPost = originalPost.toBuilder()
                .title(request.title())
                .fabricType(FabricType.getTypeByString(request.fabricType()))
                .unit(request.unit())
                .price(request.price())
                .content(request.content())
                .isPublished(isPublished)
                .transactionTypes(TransactionTypes.updateTransactionTypesWithString(
                        originalPost.getTransactionTypes(), request.transactionTypes()))
                .photos(updatedPhotos)
                .build();

        PostEntity updatedPost = postRepository.save(editedPost);

        return postConverter.toPostResponse(updatedPost);
    }

    @Transactional
    public boolean deletePostIfExistsById(Long postId){
        if (postRepository.existsById(postId)){
            postRepository.deleteById(postId);
            return true;
        } else {
            return false;
        }
    }
}
