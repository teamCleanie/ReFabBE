package cleanie.repatch.draft.model.request;

import cleanie.repatch.common.validation.annotation.ValidEnum;
import cleanie.repatch.common.validation.annotation.ValidEnumSet;
import cleanie.repatch.draft.domain.enums.DraftTransactionType;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;

import java.util.List;
import java.util.Set;

public record DraftPostRequest(
        @ValidEnum(enumClass = PostType.class) PostType postType,
        @ValidEnum(enumClass = FabricType.class) FabricType fabricType,
        String title,
        String unit,
        String price,
        String content,
        @ValidEnumSet(enumClass = DraftTransactionType.class) Set<DraftTransactionType> draftTransactionTypes,
        List<Long> photoIds
) {}
