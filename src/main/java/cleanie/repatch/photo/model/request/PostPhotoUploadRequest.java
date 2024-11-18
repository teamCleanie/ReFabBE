package cleanie.repatch.photo.model.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostPhotoUploadRequest(
        List<MultipartFile> photos
) { }
