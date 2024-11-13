package cleanie.repatch.photo.component;

import cleanie.repatch.photo.domain.PhotoExtension;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;

@Component
public class PhotoValidator {

    private final EnumSet<PhotoExtension> ALLOWED_EXTENSIONS = EnumSet.allOf(PhotoExtension.class);

    public boolean validateFileAsImage(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            return false;
        }
        return hasValidExtension(originalFileName);
    }

    public boolean hasValidExtension(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.'));

        return ALLOWED_EXTENSIONS.stream()
                .anyMatch(ex -> extension.endsWith(ex.getExtension()));
    }
}
