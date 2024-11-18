package cleanie.repatch.common.validation.validator;

import cleanie.repatch.common.validation.annotation.ValidEnumSet;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumIterableValidator implements ConstraintValidator<ValidEnumSet, Iterable<String>> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnumSet annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(Iterable<String> values, ConstraintValidatorContext context) {
        if (values == null) return false;

        boolean allValid = true;
        for (String value : values) {
            if (!isValidEnumValue(value)) {
                allValid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("허용되지 않는 타입: " + value)
                        .addConstraintViolation();
            }
        }

        return allValid;
    }

    private boolean isValidEnumValue(String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(en -> en.name().equalsIgnoreCase(value));
    }
}
