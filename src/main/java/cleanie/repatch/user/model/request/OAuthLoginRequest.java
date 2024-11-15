package cleanie.repatch.user.model.request;

import cleanie.repatch.user.model.OAuthProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OAuthLoginRequest {

    @NotNull(message = "소셜 로그인 타입이 누락되었습니다.")
    private OAuthProvider provider;

    @NotBlank(message = "로그인을 위한 액세스 토큰이 누락되었습니다.")
    private String accessToken;

    public OAuthLoginRequest(OAuthProvider provider, String accessToken) {
        this.provider = provider;
        this.accessToken = accessToken;
    }
}
