package cleanie.repatch.photo.component;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.photo.repository.PhotoRepository;
import cleanie.repatch.post.model.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostPhotoManager {

    private final PhotoRepository photoRepository;

    public List<PhotoEntity> getPhotoEntitiesFromIds(List<Long> photoIds) {

        return photoIds.stream()
                .map(id -> photoRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_REQUEST)))
                .toList();
    }

    public void addPostIdToPhotoEntities(List<PhotoEntity> photoEntities, Long postId) {
        photoEntities.forEach(photo -> addPostIdToPhotoEntity(photo, postId));
    }

    public PhotoEntity addPostIdToPhotoEntity(PhotoEntity photoEntity, Long postId){
        PhotoEntity updatedPhoto = photoEntity.toBuilder()
                .postId(postId)
                .build();

        return photoRepository.save(updatedPhoto);
    }

    public List<String> getImageUrlFromEntities(List<PhotoEntity> photoEntities){

        return photoEntities.stream()
                .map(PhotoEntity::getImageUrl)
                .toList();
    }

    public List<PhotoEntity> updatePostIds(Long postId, PostRequest request) {
        List<PhotoEntity> photos = getPhotoEntitiesFromIds(request.photoIds());
        List<PhotoEntity> updatedPhotos = new ArrayList<>();
        List<PhotoEntity> existingPhotos = new ArrayList<>();

        for (PhotoEntity photo : photos) {
            if (photo.getPostId() == null) {
                updatedPhotos.add(addPostIdToPhotoEntity(photo, postId));
            } else {
                existingPhotos.add(photo);
            }
        }
            updatedPhotos.addAll(existingPhotos);

            return updatedPhotos;
    }
}
