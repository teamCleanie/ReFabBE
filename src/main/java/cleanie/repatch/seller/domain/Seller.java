package cleanie.repatch.seller.domain;

import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.domain.UserType;
import cleanie.repatch.user.model.OAuthProvider;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SELLER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends User {

    private String nickname;

    public Seller(String name, String nickname, String imageUrl, OAuthProvider provider, String socialLoginId) {
        super(name, imageUrl, provider, socialLoginId);
        this.nickname = nickname;
    }

    @Override
    public UserType getUserType() {
        return UserType.SELLER;
    }
}
