package cleanie.repatch.user.domain;

import cleanie.repatch.buyer.domain.Buyer;
import cleanie.repatch.seller.domain.Seller;
import cleanie.repatch.setting.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("User 객체가 각 구현체로 잘 구현이 되는지")
    @Test
    @UnitTest
    void userCreateTest() {
        User seller = new Seller("test@test.com", "tester", "test.jpg", "kakao");
        User buyer = new Buyer("test@test.com", "tester", "test.jpg", "kakao");
        assertThat(seller.getUserType()).isEqualTo(UserType.SELLER);
        assertThat(buyer.getUserType()).isEqualTo(UserType.BUYER);
    }
}
