package cleanie.repatch.common.exception;

import cleanie.repatch.common.exception.model.ExceptionCode;
import lombok.Getter;

@Getter
public class FileTypeMismatchException extends RuntimeException {

    private final int code;
    private final String message;

    public FileTypeMismatchException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
