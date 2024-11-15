package cleanie.repatch.user.service;

import cleanie.repatch.common.exception.EntityNotFoundException;
import cleanie.repatch.common.security.component.JwtTokenProvider;
import cleanie.repatch.user.client.OAuthApiClient;
import cleanie.repatch.user.component.UserReader;
import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.model.OAuthUserInfo;
import cleanie.repatch.user.model.request.OAuthLoginRequest;
import cleanie.repatch.user.model.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuthClientService clientService;
    private final UserReader reader;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse loginOAuth(OAuthLoginRequest request) {
        OAuthApiClient client = clientService.getClient(request);
        OAuthUserInfo userInfo = client.getUserInfo(request);

        try {
            User user = reader.findUser(userInfo);
            String accessToken = getAccessToken(user);
            String refreshToken = getRefreshToken(user);
            user.updateRefreshToken(refreshToken);
            return AuthResponse.success(accessToken, refreshToken);
        } catch (EntityNotFoundException e) {
            return AuthResponse.shouldSignUp();
        }
    }

    private String getAccessToken(User user) {
        return jwtTokenProvider.createAccessToken(user.getId(), user.getUserType());
    }

    private String getRefreshToken(User user) {
        return jwtTokenProvider.createRefreshToken();
    }
}
