package cleanie.repatch.user.component;

import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.model.OAuthUserInfo;
import cleanie.repatch.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User findUser(OAuthUserInfo info) throws Exception {
        return userRepository.findByProviderAndSocialLoginId(info.getProvider(), info.getId())
                .orElseThrow(() -> new Exception("User not found"));

    }
}
