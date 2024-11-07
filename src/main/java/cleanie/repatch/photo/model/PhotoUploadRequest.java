package cleanie.repatch.photo.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PhotoUploadRequest(
        List<MultipartFile> photos
) { }
