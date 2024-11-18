package cleanie.repatch.post.model.request;

import cleanie.repatch.common.validation.annotation.ValidEnum;
import cleanie.repatch.common.validation.annotation.ValidEnumSet;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.post.domain.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record PostRequest(
        @NotBlank
        @ValidEnum(enumClass = PostType.class) PostType postType,
        @NotBlank
        @ValidEnum(enumClass = FabricType.class) FabricType fabricType,
        @NotBlank String title,
        @NotBlank String unit,
        @NotBlank String price,
        @NotBlank String content,
        @NotBlank
        @ValidEnumSet(enumClass = TransactionType.class) Set<TransactionType> transactionTypes,
        @NotBlank List<Long> photoIds
) {}
