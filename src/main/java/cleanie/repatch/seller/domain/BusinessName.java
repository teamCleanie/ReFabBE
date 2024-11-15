package cleanie.repatch.seller.domain;

import cleanie.repatch.common.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static cleanie.repatch.common.exception.model.ExceptionCode.INVALID_USER_BUSINESS_NAME;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BusinessName {

    private static final int MAX_LENGTH = 30;

    @Column(name = "display_name")
    private final String value;

    public BusinessName(final String businessName) {
        validate(businessName);
        this.value = businessName;
    }

    private void validate(final String businessName) {
        if (isOnlyNumbers(businessName) || isOverThenMaxLength(businessName) || businessName.contains(" ")) {
            throw new BadRequestException(INVALID_USER_BUSINESS_NAME);
        }
    }

    private boolean isOnlyNumbers(final String businessName) {
        return businessName.matches("^[0-9]*$");
    }

    private boolean isOverThenMaxLength(final String businessName) {
        return businessName.length() > 30;
    }
}
