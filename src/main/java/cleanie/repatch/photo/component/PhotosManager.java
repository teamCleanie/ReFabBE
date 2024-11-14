package cleanie.repatch.photo.component;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PhotosManager {

    private final PhotoRepository photoRepository;

    public List<Photo> getPhotoListFromIds(List<Long> photoIds) {

        return photoIds.stream()
                .map(id -> photoRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException(ExceptionCode.FILE_NOT_FOUND)))
                .toList();
    }

    public void addPostIdToPhotoList(List<Photo> photos, Long postId) {
        photos.forEach(photo -> addPostIdToPhoto(photo, postId));
    }

    public void addPostIdToPhoto(Photo photo, Long postId){
        photo.addPostIdToPhoto(photo, postId);
    }

    public void addDraftIdToPhotoList(List<Photo> photos, Long draftId) {
        photos.forEach(photo -> addDraftIdToPhoto(photo, draftId));
    }

    public void addDraftIdToPhoto(Photo photo, Long draftId) {
        photo.addDraftIdToPhoto(photo, draftId);
    }

    public void removeDraftIdFromPhotoList(List<Photo> photos) {
        photos.forEach(photo -> removeDraftIdFromPhoto(photo));
    }

    public void removeDraftIdFromPhoto(Photo photo) {
        photo.removeDraftIdFromPhoto(photo);
    }

    public List<Photo> updatePostIds(Long postId, List<Long> photoIds) {
        return getPhotoListFromIds(photoIds).stream()
                .peek(photo -> addPostIdToPhoto(photo, postId))
                .toList();
    }

    public List<Photo> updateDraftIds(Long draftId, List<Long> photoIds) {
        return getPhotoListFromIds(photoIds).stream()
                .peek(photo -> addDraftIdToPhoto(photo, draftId))
                .toList();
    }
}
