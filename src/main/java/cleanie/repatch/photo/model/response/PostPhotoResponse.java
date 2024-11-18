package cleanie.repatch.photo.model.response;

import lombok.Builder;

@Builder
public record PostPhotoResponse(
        Long id,
        String imageUrl
) {}
