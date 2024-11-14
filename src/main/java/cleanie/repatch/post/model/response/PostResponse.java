package cleanie.repatch.post.model.response;

import cleanie.repatch.photo.model.response.PhotoResponse;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
public record PostResponse(
        Long id,
        PostType postType,
        String title,
        FabricType fabricType,
        String unit,
        String price,
        String content,
        Set<String> transactionTypes,
        List<PhotoResponse> photos,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
