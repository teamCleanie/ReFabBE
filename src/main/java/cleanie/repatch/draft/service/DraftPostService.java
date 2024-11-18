package cleanie.repatch.draft.service;

import cleanie.repatch.common.exception.EntityNotFoundException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.draft.domain.DraftPost;
import cleanie.repatch.draft.model.request.DraftPostRequest;
import cleanie.repatch.draft.model.response.DraftPostResponse;
import cleanie.repatch.photo.component.PhotosManager;
import cleanie.repatch.photo.domain.DraftPhotos;
import cleanie.repatch.photo.domain.PostPhoto;
import cleanie.repatch.draft.model.response.DraftPostIdResponse;
import cleanie.repatch.draft.repository.DraftPostRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DraftPostService {

    private final DraftPostRepository draftPostRepository;
    private final PhotosManager photosManager;

    @Transactional(readOnly = true)
    public DraftPostResponse viewDraftPost(Long draftId) {
        DraftPost draftPost = findDraftPostOrThrowException(draftId);

        return draftPost.toDraftResponse();
    }

    @Transactional(readOnly = true)
    public DraftPost findDraftPostOrThrowException(Long draftId) {
        return draftPostRepository.findById(draftId).orElseThrow(
                () -> new EntityNotFoundException(ExceptionCode.NOT_FOUND));
    }

    @Transactional
    public DraftPostIdResponse saveDraftPost(DraftPostRequest request) {
        List<PostPhoto> postPhotoList = photosManager.getPhotoListFromIds(request.photoIds());
        DraftPost draftPost = draftPostRepository.save(DraftPost.toNewDraft(request));

        photosManager.addDraftIdToPhotoList(postPhotoList, draftPost.getId());

        return draftPost.toDraftIdResponse();
    }

    @Transactional
    public DraftPostIdResponse updateDraftPost(DraftPostRequest request, Long draftId) {
        DraftPost draftPost = findDraftPostOrThrowException(draftId);
        List<PostPhoto> updatedPostPhotos = photosManager.updateDraftIds(draftPost.getId(), request.photoIds());
        DraftPhotos photos = new DraftPhotos(updatedPostPhotos);

        draftPost.updateDraft(request, photos);

        return draftPost.toDraftIdResponse();
    }

    @Transactional
    public void deleteDraftPostById(Long draftId) {
        draftPostRepository.findById(draftId).ifPresentOrElse(
                draftPostRepository::delete,
                () -> {
                    throw new EntityNotFoundException(ExceptionCode.NOT_FOUND);
                }
        );
    }
}
