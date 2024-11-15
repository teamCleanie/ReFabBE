package cleanie.repatch.seller.domain;

import cleanie.repatch.common.exception.BadRequestException;

import static cleanie.repatch.common.exception.model.ExceptionCode.INVALID_USER_BUSINESS_NAME;

public class BusinessName {

    private static final int MAX_LENGTH = 30;

    private final String businessName;

    public BusinessName(String businessName) {
        validate(businessName);
        this.businessName = businessName;
    }

    private void validate(String businessName) {
        if (isOnlyNumbers(businessName) || isOverThenMaxLength(businessName) || businessName.contains(" ")) {
            throw new BadRequestException(INVALID_USER_BUSINESS_NAME);
        }
    }

    private boolean isOnlyNumbers(String businessName) {
        return businessName.matches("^[0-9]*$");
    }

    private boolean isOverThenMaxLength(String businessName) {
        return businessName.length() > 30;
    }
}
