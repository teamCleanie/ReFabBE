package cleanie.repatch.user.model.response;

import cleanie.repatch.user.model.LoginStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {
    private final LoginStatus status;
    private final String accessToken;
    private final String refreshToken;

    public static AuthResponse success(String accessToken, String refreshToken) {
        return new AuthResponse(LoginStatus.SUCCESS, accessToken, refreshToken);
    }

    public static AuthResponse shouldSignUp() {
        return new AuthResponse(LoginStatus.REQUIRES_SIGNUP, null, null);
    }
}
