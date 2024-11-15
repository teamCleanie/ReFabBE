package cleanie.repatch.seller.domain;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BusinessNameTest {

    @DisplayName("사업자명이 숫자로만 이루어져 있을 경우 예외가 잘 발생하는지")
    @Test
    void businessNameWithOnlyNumber() {
        assertThatThrownBy(() -> new BusinessName("1234567890"))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ExceptionCode.INVALID_USER_BUSINESS_NAME.getMessage());
    }

    @DisplayName("사업자 명이 30자 이상이면 예외가 잘 발생하는지")
    @Test
    void businessNameWithOver30Characters() {
        assertThatThrownBy(() -> new BusinessName("1234567890123456789012345678901"))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ExceptionCode.INVALID_USER_BUSINESS_NAME.getMessage());
    }

    @DisplayName("사업자명에 공백이 있는 경우 예외가 잘 발생하는지")
    @Test
    void businessNameWithSpace() {
        assertThatThrownBy(() -> new BusinessName("사업자명 테스트"))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ExceptionCode.INVALID_USER_BUSINESS_NAME.getMessage());
    }
}
