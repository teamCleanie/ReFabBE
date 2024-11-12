package cleanie.repatch.user.repository;

import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.model.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndSocialLoginId(OAuthProvider provider, String socialLoginId);
}
