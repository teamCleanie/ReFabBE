package cleanie.repatch.user.client;

import cleanie.repatch.user.model.OAuthProvider;
import cleanie.repatch.user.model.OAuthUserInfo;

public interface OAuthApiClient {
    OAuthProvider getProvider();

    OAuthUserInfo getUserInfo(String accessToken);
}
