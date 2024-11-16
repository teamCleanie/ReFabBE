package cleanie.repatch.post.domain;

import cleanie.repatch.common.domain.BaseEntity;
import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.draft.domain.DraftPost;
import cleanie.repatch.photo.domain.Photos;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.post.domain.enums.TransactionTypes;
import cleanie.repatch.post.model.request.PostRequest;
import cleanie.repatch.post.model.response.PostIdResponse;
import cleanie.repatch.post.model.response.PostResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.stream.Stream;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private FabricType fabricType;

    private String title;
    private String unit;
    private String price;
    private String content;

    @Embedded
    private TransactionTypes transactionTypes;

    @Embedded
    private Photos photos;

    public static Post toPost(PostRequest request) {

        return Post.builder()
                .postType(request.postType())
                .fabricType(request.fabricType())
                .title(request.title())
                .unit(request.unit())
                .price(request.price())
                .content(request.content())
                .transactionTypes(new TransactionTypes(request.transactionTypes()))
                .photos(new Photos())
                .build();
    }

    public Post updatePost(Post post, PostRequest request, Photos photos) {
        if (!validatePost(request)) {
            throw new BadRequestException(ExceptionCode.INVALID_POST_REQUEST);
        }
        TransactionTypes transactions = post.getTransactionTypes();
        post.fabricType = request.fabricType();
        post.title = request.title();
        post.unit = request.unit();
        post.price = request.price();
        post.content = request.content();
        post.transactionTypes = transactions.updateTransactionTypes(transactions, request.transactionTypes());
        post.photos = photos;

        return post;
    }

    public static Post publishDraft(DraftPost draftPost) {
        return Post.builder()
                .postType(draftPost.getPostType())
                .fabricType(draftPost.getFabricType())
                .title(draftPost.getTitle())
                .unit(draftPost.getUnit())
                .price(draftPost.getPrice())
                .content(draftPost.getContent())
                .transactionTypes(draftPost.getDraftTransactionTypes().toTransactionTypes())
                .photos(new Photos())
                .build();
    }

    public boolean validatePost(PostRequest request) {
        return Stream.of(
                request.postType().getPostEngName(),
                request.fabricType().getFabricEngName(),
                request.title(),
                request.unit(),
                request.price(),
                request.content()
        ).noneMatch(String::isBlank) && !request.photoIds().isEmpty()
                && !request.transactionTypes().isEmpty();
    }

    public PostResponse toPostResponse(Post post) {
        TransactionTypes transactions = post.getTransactionTypes();

        return new PostResponse(
                post.getId(), post.getPostType(),
                post.getTitle(), post.getFabricType(),
                post.getUnit(), post.getPrice(),
                post.getContent(), transactions.createStringSet(transactions.getTransactionTypes()),
                Photos.toPhotoResponses(post.getPhotos()),
                post.getCreatedAt(), post.getModifiedAt());
    }

    public PostIdResponse toPostIdResponse(Post post) {
        return new PostIdResponse(post.getId());
    }
}
