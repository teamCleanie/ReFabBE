package cleanie.repatch.photo.component;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Component
public class PhotoValidator {

    private final Set<String> allowedExtensions;

    public PhotoValidator() {
        this.allowedExtensions = new HashSet<>(Set.of(".jpg", ".jpeg", ".png", ".webp"));
    }

    public boolean validateFileAsImage(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            return false;
        }
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        return allowedExtensions.contains(extension);
    }
}
