package cleanie.repatch.post.component;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.draft.domain.DraftPost;
import cleanie.repatch.draft.repository.DraftPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DraftPostManager {

    private final DraftPostRepository draftPostRepository;

    @Transactional
    public DraftPost findDraftById(Long draftId) {
        return draftPostRepository.findById(draftId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.DRAFT_NOT_FOUND));
    }

    @Transactional
    public boolean deleteDraftById(Long draftId) {
        return draftPostRepository.findById(draftId)
                .map(draft -> {
                    draftPostRepository.delete(draft);
                    return true;
                }).orElse(false);
    }
}
