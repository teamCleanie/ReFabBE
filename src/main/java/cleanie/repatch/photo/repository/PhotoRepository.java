package cleanie.repatch.photo.repository;

import cleanie.repatch.photo.domain.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
}
