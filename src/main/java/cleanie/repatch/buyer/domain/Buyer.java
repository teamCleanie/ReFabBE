package cleanie.repatch.buyer.domain;

import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.domain.UserType;
import cleanie.repatch.user.model.OAuthProvider;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("BUYER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Buyer extends User {

    public Buyer(String name, String imageUrl, OAuthProvider provider, String socialLoginId) {
        super(name, imageUrl, provider, socialLoginId);
    }

    @Override
    public UserType getUserType() {
        return UserType.BUYER;
    }
}
