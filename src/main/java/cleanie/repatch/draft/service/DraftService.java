package cleanie.repatch.draft.service;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.draft.model.request.DraftRequest;
import cleanie.repatch.draft.model.response.DraftResponse;
import cleanie.repatch.photo.component.PhotosManager;
import cleanie.repatch.photo.domain.DraftPhotos;
import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.draft.domain.Draft;
import cleanie.repatch.draft.model.response.DraftIdResponse;
import cleanie.repatch.draft.repository.DraftRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DraftService {

    private final DraftRepository draftRepository;
    private final PhotosManager photosManager;

    @Transactional
    public DraftResponse viewDraft(Long draftId) {
        Draft draft = draftRepository.findById(draftId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.DRAFT_NOT_FOUND));

        return draft.toDraftResponse(draft);
    }

    @Transactional
    public DraftIdResponse saveDraft(DraftRequest request) {
        List<Photo> photoList = photosManager.getPhotoListFromIds(request.photoIds());
        Draft draft= draftRepository.save(Draft.toNewDraft(request));

        photosManager.addDraftIdToPhotoList(photoList, draft.getId());

        return draft.toDraftIdResponse(draft);
    }

    @Transactional
    public DraftIdResponse updateDraft(DraftRequest request, Long draftId) {
        Draft originalDraft = draftRepository.findById(draftId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.DRAFT_NOT_FOUND));
        List<Photo> updatedPhotos = photosManager.updateDraftIds(originalDraft.getId(), request.photoIds());
        DraftPhotos photos = new DraftPhotos(updatedPhotos);

        Draft updatedDraft = originalDraft.updateDraft(originalDraft, request, photos);

        return updatedDraft.toDraftIdResponse(updatedDraft);
    }

    @Transactional
    public boolean deleteDraftById(Long draftId) {

        return draftRepository.findById(draftId)
                .map(draft -> {
                    draftRepository.delete(draft);
                    return true;
                }).orElse(false);
    }
}
