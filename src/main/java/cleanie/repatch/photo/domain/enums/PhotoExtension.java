package cleanie.repatch.photo.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PhotoExtension {
    JPG(".jpg"),
    JPEG(".jpeg"),
    PNG(".png"),
    WEBP(".webp");

    private final String extension;
}
