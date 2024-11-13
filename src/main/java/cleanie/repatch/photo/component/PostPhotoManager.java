package cleanie.repatch.photo.component;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.Photo;
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

    public List<Photo> getPhotoEntitiesFromIds(List<Long> photoIds) {

        return photoIds.stream()
                .map(id -> photoRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_REQUEST)))
                .toList();
    }

    public void addPostIdToPhotoEntities(List<Photo> photoEntities, Long postId) {
        photoEntities.forEach(photo -> addPostIdToPhotoEntity(photo, postId));
    }

    public Photo addPostIdToPhotoEntity(Photo photo, Long postId){
        Photo updatedPhoto = photo.toBuilder()
                .postId(postId)
                .build();

        return photoRepository.save(updatedPhoto);
    }

    public List<String> getImageUrlFromEntities(List<Photo> photoEntities){

        return photoEntities.stream()
                .map(Photo::getImageUrl)
                .toList();
    }

    public List<Photo> updatePostIds(Long postId, PostRequest request) {
        List<Photo> photos = getPhotoEntitiesFromIds(request.photoIds());
        List<Photo> updatedPhotos = new ArrayList<>();
        List<Photo> existingPhotos = new ArrayList<>();

        for (Photo photo : photos) {
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
