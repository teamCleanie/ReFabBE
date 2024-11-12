package cleanie.repatch.seller.domain;

import cleanie.repatch.user.domain.User;
import cleanie.repatch.user.domain.UserType;
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

    public Seller(String email, String name, String imageUrl, String providerId) {
        super(email, name, imageUrl, providerId);
    }

    @Override
    public UserType getUserType() {
        return UserType.SELLER;
    }
}
