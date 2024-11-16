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

    public DraftPost updateDraft(DraftPost draftPost, DraftPostRequest request, DraftPhotos photos) {
        DraftTransactionTypes draftTransactions = draftPost.draftTransactionTypes;

        draftPost.fabricType = request.fabricType();
        draftPost.title = request.title();
        draftPost.unit = request.unit();
        draftPost.price = request.price();
        draftPost.content = request.content();
        draftPost.draftTransactionTypes = draftTransactions.updateDraftTransactionTypes(
                draftTransactions, request.draftTransactionTypes());
        draftPost.draftPhotos = photos;

        return draftPost;
    }

    public DraftPostResponse toDraftResponse(DraftPost draftPost) {
        DraftTransactionTypes draftTransactions = draftPost.getDraftTransactionTypes();

        return new DraftPostResponse(
                draftPost.getId(), draftPost.getPostType(),
                draftPost.getTitle(), draftPost.getFabricType(),
                draftPost.getUnit(), draftPost.getPrice(),
                draftPost.getContent(),
                draftTransactions.createStringSet(draftTransactions.getDraftTransactionTypes()),
                DraftPhotos.toPhotoResponses(draftPost.getDraftPhotos()),
                draftPost.getCreatedAt(), draftPost.getModifiedAt());
    }

    public DraftPostIdResponse toDraftIdResponse(DraftPost draftPost) {
        return new DraftPostIdResponse(draftPost.getId());
    }
}
