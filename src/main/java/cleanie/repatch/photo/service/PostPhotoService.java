package cleanie.repatch.photo.service;

import cleanie.repatch.common.s3.helper.S3FileManager;
import cleanie.repatch.photo.domain.PostPhoto;
import cleanie.repatch.photo.domain.PostPhotos;
import cleanie.repatch.photo.model.request.PostPhotoUploadRequest;
import cleanie.repatch.photo.model.response.PostPhotoResponse;
import cleanie.repatch.photo.repository.PostPhotoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostPhotoService {

    private final PostPhotoRepository postPhotoRepository;
    private final S3FileManager s3FileManager;

    @Transactional
    public List<PostPhotoResponse> uploadAndSavePhotos(PostPhotoUploadRequest request) throws IOException {
        PostPhotos postPhotos = new PostPhotos();
        for (int i = 0; i < request.photos().size(); i++){
            String fileName = "username_"+ UUID.randomUUID();
            String imageUrl = s3FileManager.uploadFile(request.photos().get(i), fileName);
            PostPhoto postPhoto = postPhotoRepository.save(PostPhoto.toPhoto(imageUrl));
            postPhotos.addPhoto(postPhoto);
        }
        return PostPhotos.toPhotoResponses(postPhotos);
    }

    @Transactional
    public boolean deletePhotoIfExistsById(Long id){
        return postPhotoRepository.findById(id)
                .map(post -> { postPhotoRepository.delete(post);
                    return true;
                }).orElse(false);
    }
}
