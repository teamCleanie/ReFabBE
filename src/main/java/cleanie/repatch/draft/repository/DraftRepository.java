package cleanie.repatch.draft.repository;

import cleanie.repatch.draft.domain.Draft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DraftRepository extends JpaRepository<Draft, Long> {
}
