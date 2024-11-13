package cleanie.repatch.user.component;

import cleanie.repatch.common.exception.EntityNotFoundException;
import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.model.OAuthUserInfo;
import cleanie.repatch.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static cleanie.repatch.common.exception.model.ExceptionCode.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public User findUser(OAuthUserInfo info) {
        return userRepository.findByProviderAndSocialLoginId(info.getProvider(), info.getId())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }
}
