package cleanie.repatch.user.domain;

import cleanie.repatch.buyer.domain.Buyer;
import cleanie.repatch.seller.domain.Seller;
import cleanie.repatch.setting.annotation.UnitTest;
import cleanie.repatch.user.model.OAuthProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("User 객체가 각 구현체로 잘 구현이 되는지")
    @Test
    @UnitTest
    void userCreateTest() {
        User seller = new Seller("tester", "test.jpg", OAuthProvider.KAKAO, "12314");
        User buyer = new Buyer("tester", "test.jpg,", OAuthProvider.NAVER, "123122");
        assertThat(seller.getUserType()).isEqualTo(UserType.SELLER);
        assertThat(buyer.getUserType()).isEqualTo(UserType.BUYER);
    }
}
