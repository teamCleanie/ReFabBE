package cleanie.repatch.post.repository;

import cleanie.repatch.post.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
