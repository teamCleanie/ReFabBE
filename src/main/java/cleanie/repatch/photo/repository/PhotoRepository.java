package cleanie.repatch.photo.repository;

import cleanie.repatch.photo.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
