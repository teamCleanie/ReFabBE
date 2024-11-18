package cleanie.repatch.photo.repository;

import cleanie.repatch.photo.domain.PostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {
}
