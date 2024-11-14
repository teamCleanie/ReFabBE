package cleanie.repatch.draft.domain;

import cleanie.repatch.common.domain.BaseEntity;
import cleanie.repatch.draft.domain.enums.DraftTransactionTypes;
import cleanie.repatch.draft.model.request.DraftRequest;
import cleanie.repatch.draft.model.response.DraftResponse;
import cleanie.repatch.photo.domain.DraftPhotos;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.draft.model.response.DraftIdResponse;
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
public class Draft extends BaseEntity {

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

    public static Draft toNewDraft(DraftRequest request) {

        return Draft.builder()
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

    public Draft updateDraft(Draft draft, DraftRequest request, DraftPhotos photos) {
        DraftTransactionTypes draftTransactions = draft.draftTransactionTypes;

        draft.fabricType = request.fabricType();
        draft.title = request.title();
        draft.unit = request.unit();
        draft.price = request.price();
        draft.content = request.content();
        draft.draftTransactionTypes = draftTransactions.updateDraftTransactionTypes(
                draftTransactions, request.draftTransactionTypes());
        draft.draftPhotos = photos;

        return draft;
    }

    public DraftResponse toDraftResponse(Draft draft) {
        DraftTransactionTypes draftTransactions = draft.getDraftTransactionTypes();

        return new DraftResponse(
                draft.getId(), draft.getPostType(),
                draft.getTitle(), draft.getFabricType(),
                draft.getUnit(), draft.getPrice(),
                draft.getContent(),
                draftTransactions.createStringSet(draftTransactions.getDraftTransactionTypes()),
                DraftPhotos.toPhotoResponses(draft.getDraftPhotos()),
                draft.getCreatedAt(), draft.getModifiedAt());
    }

    public DraftIdResponse toDraftIdResponse(Draft draft) {
        return new DraftIdResponse(draft.getId());
    }
}
