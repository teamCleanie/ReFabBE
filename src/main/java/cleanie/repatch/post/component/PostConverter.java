package cleanie.repatch.post.component;

import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.photo.component.PostPhotoManager;
import cleanie.repatch.post.domain.Post;
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

    public Post toPostEntity(PostRequest request, boolean isPublished){
        List<Photo> photos = (request.photoIds().isEmpty()) ?
                new ArrayList<>() :
                postPhotoManager.getPhotoEntitiesFromIds(request.photoIds());

        return Post.builder()
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

    public Post toEditedPostEntity(Post originalPost, PostRequest request,
                                   boolean isPublished, List<Photo> updatedPhotos) {
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

    public PostResponse toPostResponse(Post post){
        List<String> imageUrls = postPhotoManager.getImageUrlFromEntities(post.getPhotos());
        Set<String> transactionTypes = TransactionTypes.getStringFromTransactionTypes(post.getTransactionTypes());

        return PostResponse.builder()
                .id(post.getId())
                .postType(post.getPostType().getPostKorName())
                .title(post.getTitle())
                .fabricType(post.getFabricType().getFabricKorName())
                .unit(post.getUnit())
                .price(post.getPrice())
                .content(post.getContent())
                .tradeTypes(transactionTypes)
                .isPublished(post.getIsPublished())
                .photos(imageUrls)
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    public PostIdResponse toPostIdResponse(Long postId) {
        return PostIdResponse.builder()
                .postId(postId)
                .build();
    }
}
