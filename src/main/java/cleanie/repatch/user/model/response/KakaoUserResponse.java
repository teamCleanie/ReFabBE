package cleanie.repatch.user.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserResponse {
    private Long id;
    private KakaoAccount kakaoAccount;

    public String getName() {
        return kakaoAccount.getName();
    }

    public String getProfileImageUrl() {
        return kakaoAccount.getProfileImageUrl();
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoAccount {
        private String name;
        private Profile profile;

        public String getProfileImageUrl() {
            return profile.getProfileImageUrl();
        }
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Profile {
        private String profileImageUrl;
    }
}
