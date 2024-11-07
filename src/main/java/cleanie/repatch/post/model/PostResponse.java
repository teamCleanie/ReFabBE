package cleanie.repatch.post.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
public record PostResponse(
        Long id,
        String postType,
        String title,
        String fabricType,
        String unit,
        String price,
        String content,
        Set<String> tradeTypes,
        Boolean isDraft,
        List<String> photos,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
