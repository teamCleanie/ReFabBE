package cleanie.repatch.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthUserInfo {
    private final OAuthProvider provider;
    private final String id;
    private final String name;
    private final String profileImageUrl;
}
