package cleanie.repatch.draft.model.request;

import cleanie.repatch.draft.domain.enums.DraftTransactionType;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;

import java.util.List;
import java.util.Set;

public record DraftPostRequest(
        PostType postType,
        FabricType fabricType,
        String title,
        String unit,
        String price,
        String content,
        Set<DraftTransactionType> draftTransactionTypes,
        List<Long> photoIds
) {}
