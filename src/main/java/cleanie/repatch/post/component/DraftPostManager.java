package cleanie.repatch.post.component;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.draft.domain.Draft;
import cleanie.repatch.draft.repository.DraftRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DraftPostManager {

    private final DraftRepository draftRepository;

    @Transactional
    public Draft findDraftById(Long draftId) {
        return draftRepository.findById(draftId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.DRAFT_NOT_FOUND));
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
