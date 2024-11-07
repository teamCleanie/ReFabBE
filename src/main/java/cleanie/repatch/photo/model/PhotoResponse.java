package cleanie.repatch.photo.model;

import lombok.Builder;

@Builder
public record PhotoResponse(
        Long id,
        String imageUrl
) {}
