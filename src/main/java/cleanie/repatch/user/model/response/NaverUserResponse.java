package cleanie.repatch.user.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverUserResponse {
    private NaverUserResponseBody response;

    public String getId() {
        return response.getId();
    }

    public String getName() {
        return response.getName();
    }

    public String getProfileImage() {
        return response.getProfileImage();
    }

    @Getter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class NaverUserResponseBody {
        private String id;
        private String name;
        private String profileImage;
    }
}
