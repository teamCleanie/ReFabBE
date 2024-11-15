package cleanie.repatch.buyer.domain;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.setting.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @DisplayName("닉네임에 공백이 포함된 경우 예외가 잘 발생하는지")
    @Test
    @UnitTest
    void nicknameWithBlank() {
        assertThatThrownBy(() -> new Nickname("테스트 닉네임"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionCode.INVALID_USER_NICKNAME.getMessage());
    }

    @DisplayName("닉네임에 특수문자가 포함된 경우 예외가 잘 발생하는지")
    @ParameterizedTest
    @UnitTest
    @ValueSource(strings = {"나는특별유저★", "@@최고유저", "admin좌!@#"})
    void nicknameWithSpecialCharacter(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionCode.INVALID_USER_NICKNAME.getMessage());
    }

    @DisplayName("닉네임의 길이 규칙을 만족하지 않으면 예외가 잘 발생하는지")
    @ParameterizedTest
    @UnitTest
    @ValueSource(strings = {"나", "나는야최고의슈퍼환경전문리셀러"})
    void nicknameWithInvalidLength(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionCode.INVALID_USER_NICKNAME.getMessage());
    }

    @DisplayName("욕설 / 비속어가 포함 되어있는 경우 예외가 잘 발생하는지")
    @Test
    @UnitTest
    void nicknameWithBadWord() {
        assertThatThrownBy(() -> new Nickname("ㅗ"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionCode.INVALID_USER_NICKNAME.getMessage());
    }
}
