package cleanie.repatch.photo.service;

import cleanie.repatch.common.s3.helper.S3FileManager;
import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.photo.domain.Photos;
import cleanie.repatch.photo.model.request.PhotoUploadRequest;
import cleanie.repatch.photo.model.response.PhotoResponse;
import cleanie.repatch.photo.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final S3FileManager s3FileManager;

    @Transactional
    public List<PhotoResponse> uploadAndSavePhotos(PhotoUploadRequest request) throws IOException {
        Photos photos = new Photos();
        for (int i = 0; i < request.photos().size(); i++){
            String fileName = "username_"+ UUID.randomUUID();
            String imageUrl = s3FileManager.uploadFile(request.photos().get(i), fileName);
            Photo photo = photoRepository.save(Photo.toPhoto(imageUrl));
            photos.addPhoto(photo);
        }
        return Photos.toPhotoResponses(photos);
    }

    @Transactional
    public boolean deletePhotoIfExistsById(Long id){
        return photoRepository.findById(id)
                .map(post -> { photoRepository.delete(post);
                    return true;
                }).orElse(false);
    }
}
