package cleanie.repatch.user.client;

import cleanie.repatch.user.model.OAuthProvider;
import cleanie.repatch.user.model.OAuthUserInfo;
import cleanie.repatch.user.model.request.OAuthLoginRequest;

public interface OAuthApiClient {
    OAuthProvider getProvider();

    OAuthUserInfo getUserInfo(OAuthLoginRequest request);
}
