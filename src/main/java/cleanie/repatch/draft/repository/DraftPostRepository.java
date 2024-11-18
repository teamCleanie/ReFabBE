package cleanie.repatch.draft.repository;

import cleanie.repatch.draft.domain.DraftPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftPostRepository extends JpaRepository<DraftPost, Long> {
}
