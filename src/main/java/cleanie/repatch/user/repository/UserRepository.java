package cleanie.repatch.user.repository;

import cleanie.repatch.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
