package cleanie.repatch.seller.domain;

import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.domain.UserType;
import cleanie.repatch.user.model.OAuthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SELLER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends User {

    @Embedded
    private BusinessName businessName;

    public Seller(String name, BusinessName businessName, String imageUrl, OAuthProvider provider, String socialLoginId) {
        super(name, imageUrl, provider, socialLoginId);
        this.businessName = businessName;
    }

    @Override
    public UserType getUserType() {
        return UserType.SELLER;
    }
}
