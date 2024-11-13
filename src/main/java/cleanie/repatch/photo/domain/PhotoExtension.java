package cleanie.repatch.photo.domain;

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
