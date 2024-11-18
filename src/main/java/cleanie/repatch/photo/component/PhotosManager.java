package cleanie.repatch.photo.component;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.photo.domain.PostPhoto;
import cleanie.repatch.photo.repository.PostPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PhotosManager {

    private final PostPhotoRepository postPhotoRepository;

    public List<PostPhoto> getPhotoListFromIds(List<Long> photoIds) {

        return photoIds.stream()
                .map(id -> postPhotoRepository.findById(id)
                        .orElseThrow(() -> new BadRequestException(ExceptionCode.FILE_NOT_FOUND)))
                .toList();
    }

    public void addPostIdToPhotoList(List<PostPhoto> postPhotos, Long postId) {
        postPhotos.forEach(photo -> addPostIdToPhoto(photo, postId));
    }

    public void addPostIdToPhoto(PostPhoto postPhoto, Long postId){
        postPhoto.addPostIdToPhoto(postPhoto, postId);
    }

    public void addDraftIdToPhotoList(List<PostPhoto> postPhotos, Long draftId) {
        postPhotos.forEach(photo -> addDraftIdToPhoto(photo, draftId));
    }

    public void addDraftIdToPhoto(PostPhoto postPhoto, Long draftId) {
        postPhoto.addDraftIdToPhoto(postPhoto, draftId);
    }

    public void removeDraftIdFromPhotoList(List<PostPhoto> postPhotos) {
        postPhotos.forEach(photo -> removeDraftIdFromPhoto(photo));
    }

    public void removeDraftIdFromPhoto(PostPhoto postPhoto) {
        postPhoto.removeDraftIdFromPhoto(postPhoto);
    }

    public List<PostPhoto> updatePostIds(Long postId, List<Long> photoIds) {
        return getPhotoListFromIds(photoIds).stream()
                .peek(photo -> addPostIdToPhoto(photo, postId))
                .toList();
    }

    public List<PostPhoto> updateDraftIds(Long draftId, List<Long> photoIds) {
        return getPhotoListFromIds(photoIds).stream()
                .peek(photo -> addDraftIdToPhoto(photo, draftId))
                .toList();
    }
}
