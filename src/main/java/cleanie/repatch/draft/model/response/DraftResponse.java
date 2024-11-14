package cleanie.repatch.draft.model.response;

import cleanie.repatch.photo.model.response.PhotoResponse;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record DraftResponse(
        Long id,
        PostType postType,
        String title,
        FabricType fabricType,
        String unit,
        String price,
        String content,
        Set<String> draftTransactionTypes,
        List<PhotoResponse> photos,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
