package cleanie.repatch.draft.domain;

import cleanie.repatch.common.domain.BaseEntity;
import cleanie.repatch.draft.domain.enums.DraftTransactionTypes;
import cleanie.repatch.draft.model.request.DraftPostRequest;
import cleanie.repatch.draft.model.response.DraftPostResponse;
import cleanie.repatch.photo.domain.DraftPhotos;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.draft.model.response.DraftPostIdResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DraftPost extends BaseEntity {

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
    private DraftTransactionTypes draftTransactionTypes;

    @Embedded
    private DraftPhotos draftPhotos;

    public static DraftPost toNewDraft(DraftPostRequest request) {

        return DraftPost.builder()
                .postType(request.postType())
                .fabricType(request.fabricType())
                .title(request.title())
                .unit(request.unit())
                .price(request.price())
                .content(request.content())
                .draftTransactionTypes(new DraftTransactionTypes(request.draftTransactionTypes()))
                .draftPhotos(new DraftPhotos())
                .build();
    }

    public void updateDraft(DraftPostRequest request, DraftPhotos photos) {
        DraftTransactionTypes draftTransactions = this.draftTransactionTypes;

        this.fabricType = request.fabricType();
        this.title = request.title();
        this.unit = request.unit();
        this.price = request.price();
        this.content = request.content();
        this.draftTransactionTypes = draftTransactions.updateDraftTransactionTypes(
                request.draftTransactionTypes());
        this.draftPhotos = photos;
    }

    public DraftPostResponse toDraftResponse() {
        DraftTransactionTypes draftTransactions = this.getDraftTransactionTypes();

        return new DraftPostResponse(
                this.id, this.postType,
                this.title, this.fabricType,
                this.unit, this.price,
                this.content,
                draftTransactions.createStringSet(draftTransactions.getDraftTransactionTypes()),
                DraftPhotos.toPhotoResponses(this.draftPhotos),
                this.getCreatedAt(), this.getModifiedAt());
    }

    public DraftPostIdResponse toDraftIdResponse() {
        return new DraftPostIdResponse(this.id);
    }
}
