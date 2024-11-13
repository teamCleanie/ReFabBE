package cleanie.repatch.post.component;

import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.photo.component.PostPhotoManager;
import cleanie.repatch.post.domain.PostEntity;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.post.domain.enums.TransactionTypes;
import cleanie.repatch.post.model.response.PostIdResponse;
import cleanie.repatch.post.model.request.PostRequest;
import cleanie.repatch.post.model.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostConverter {

    private final PostPhotoManager postPhotoManager;

    public PostEntity toPostEntity(PostRequest request, boolean isPublished){
        List<PhotoEntity> photos = (request.photoIds().isEmpty()) ?
                new ArrayList<>() :
                postPhotoManager.getPhotoEntitiesFromIds(request.photoIds());

        return PostEntity.builder()
                .postType(PostType.getTypeByString(request.postType()))
                .title(request.title())
                .fabricType(FabricType.getTypeByString(request.fabricType()))
                .unit(request.unit())
                .price(request.price())
                .content(request.content())
                .isPublished(isPublished)
                .photos(photos)
                .transactionTypes(TransactionTypes.createTransactionTypesFromString(request.transactionTypes()))
                .build();
    }

    public PostEntity toEditedPostEntity(PostEntity originalPost, PostRequest request,
                                         boolean isPublished, List<PhotoEntity> updatedPhotos) {
        return originalPost.toBuilder()
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
    }

    public PostResponse toPostResponse(PostEntity postEntity){
        List<String> imageUrls = postPhotoManager.getImageUrlFromEntities(postEntity.getPhotos());
        Set<String> transactionTypes = TransactionTypes.getStringFromTransactionTypes(postEntity.getTransactionTypes());

        return PostResponse.builder()
                .id(postEntity.getId())
                .postType(postEntity.getPostType().getPostKorName())
                .title(postEntity.getTitle())
                .fabricType(postEntity.getFabricType().getFabricKorName())
                .unit(postEntity.getUnit())
                .price(postEntity.getPrice())
                .content(postEntity.getContent())
                .tradeTypes(transactionTypes)
                .isPublished(postEntity.getIsPublished())
                .photos(imageUrls)
                .createdAt(postEntity.getCreatedAt())
                .modifiedAt(postEntity.getModifiedAt())
                .build();
    }

    public PostIdResponse toPostIdResponse(Long postId) {
        return PostIdResponse.builder()
                .postId(postId)
                .build();
    }
}
