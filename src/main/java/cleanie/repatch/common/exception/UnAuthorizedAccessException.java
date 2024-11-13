package cleanie.repatch.common.exception;

import cleanie.repatch.common.exception.model.ExceptionCode;
import jakarta.security.auth.message.AuthException;
import lombok.Getter;

@Getter
public class UnAuthorizedAccessException extends RuntimeException {

    private final int code;
    private final String message;

    public UnAuthorizedAccessException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}

