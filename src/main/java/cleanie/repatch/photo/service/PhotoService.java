package cleanie.repatch.photo.service;

import cleanie.repatch.common.s3.helper.S3FileManager;
import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.photo.model.PhotoUploadRequest;
import cleanie.repatch.photo.model.PhotoResponse;
import cleanie.repatch.photo.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoConverter photoConverter;
    private final S3FileManager s3FileManager;

    @Transactional
    public List<PhotoResponse> uploadAndSavePhotos(PhotoUploadRequest request) throws IOException {
        List<PhotoResponse> responses = new ArrayList<>();
        for (int i = 0; i < request.photos().size(); i++){
            String fileName = "username_"+ UUID.randomUUID();
            String imageUrl = s3FileManager.uploadFile(request.photos().get(i), fileName);
            PhotoEntity photo = photoRepository.save(photoConverter.toPhotoEntity(imageUrl));
            responses.add(photoConverter.toPhotoResponse(photo));
        }
        return responses;
    }

    @Transactional
    public boolean deletePhotoIfExistsById(Long id){
        if (photoRepository.existsById(id)){
            photoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
