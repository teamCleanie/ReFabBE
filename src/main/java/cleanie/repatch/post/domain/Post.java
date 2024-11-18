package cleanie.repatch.post.domain;

import cleanie.repatch.common.domain.BaseEntity;
import cleanie.repatch.photo.domain.PostPhotos;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.post.domain.enums.TransactionTypes;
import cleanie.repatch.post.model.request.PostRequest;
import cleanie.repatch.post.model.response.PostIdResponse;
import cleanie.repatch.post.model.response.PostResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private PostPhotos postPhotos;

    public static Post toPost(PostRequest request) {

        return Post.builder()
                .postType(request.postType())
                .fabricType(request.fabricType())
                .title(request.title())
                .unit(request.unit())
                .price(request.price())
                .content(request.content())
                .transactionTypes(new TransactionTypes(request.transactionTypes()))
                .postPhotos(new PostPhotos())
                .build();
    }

    public void updatePost(PostRequest request, PostPhotos postPhotos) {
        TransactionTypes transactions = this.getTransactionTypes();
        this.fabricType = request.fabricType();
        this.title = request.title();
        this.unit = request.unit();
        this.price = request.price();
        this.content = request.content();
        this.transactionTypes = transactions.updateTransactionTypes(request.transactionTypes());
        this.postPhotos = postPhotos;
    }

    public PostResponse toPostResponse() {
        TransactionTypes transactions = this.getTransactionTypes();

        return new PostResponse(
                this.id, this.postType,
                this.title, this.fabricType,
                this.unit, this.price,
                this.content, transactions.createStringSet(transactions.getTransactionTypes()),
                PostPhotos.toPhotoResponses(this.postPhotos),
                this.getCreatedAt(), this.getModifiedAt());
    }

    public PostIdResponse toPostIdResponse() {
        return new PostIdResponse(this.id);
    }
}
