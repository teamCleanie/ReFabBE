package cleanie.repatch.photo.model.response;

import lombok.Builder;

@Builder
public record PhotoResponse(
        Long id,
        String imageUrl
) {}
