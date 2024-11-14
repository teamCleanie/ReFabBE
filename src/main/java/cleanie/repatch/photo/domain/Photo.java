package cleanie.repatch.photo.domain;

import cleanie.repatch.common.domain.BaseEntity;
import cleanie.repatch.common.exception.FileTypeMismatchException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.enums.PhotoExtension;
import cleanie.repatch.photo.model.response.PhotoResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private Long postId;
    private Long draftId;

    public static Photo toPhoto(String imageUrl) {
        if (validatePhotoUrl(imageUrl)) {
            return new Photo(null, imageUrl, null, null);
        }
        throw new FileTypeMismatchException(ExceptionCode.FILE_TYPE_NOT_SUPPORTED);
    }

    public static boolean validatePhotoUrl(String imageUrl) {
        String extension = imageUrl.substring(imageUrl.lastIndexOf('.')).toLowerCase();
        return Arrays.stream(PhotoExtension.values())
                .anyMatch(ex -> extension.endsWith(ex.getExtension()));
    }

    public void addPostIdToPhoto(Photo photo, Long postId) {
        photo.postId = postId;
    }

    public void addDraftIdToPhoto(Photo photo, Long draftId) {
        photo.draftId = draftId;
    }

    public void removeDraftIdFromPhoto(Photo photo) {
        photo.draftId = null;
    }

    public PhotoResponse toPhotoResponse(Photo photo) {
        return new PhotoResponse(photo.getId(), photo.getImageUrl());
    }
}
