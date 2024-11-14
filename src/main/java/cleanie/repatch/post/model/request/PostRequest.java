package cleanie.repatch.post.model.request;

import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.post.domain.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record PostRequest(
        @NotBlank PostType postType,
        @NotBlank FabricType fabricType,
        @NotBlank String title,
        @NotBlank String unit,
        @NotBlank String price,
        @NotBlank String content,
        @NotBlank Set<TransactionType> transactionTypes,
        @NotBlank List<Long> photoIds
) {}
