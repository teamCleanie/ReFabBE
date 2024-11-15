package cleanie.repatch.buyer.domain;

import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.domain.UserType;
import cleanie.repatch.user.model.OAuthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("BUYER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Buyer extends User {

    @Embedded
    private Nickname nickname;

    public Buyer(String name, Nickname nickname, String imageUrl, OAuthProvider provider, String socialLoginId) {
        super(name, imageUrl, provider, socialLoginId);
        this.nickname = nickname;
    }

    @Override
    public UserType getUserType() {
        return UserType.BUYER;
    }
}
