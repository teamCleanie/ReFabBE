package cleanie.repatch.common.exception;

import cleanie.repatch.common.exception.model.ExceptionCode;
import cleanie.repatch.user.model.OAuthProvider;
import lombok.Getter;

@Getter
public class OAuthApiException extends RuntimeException{

    private final int code;
    private final String message;
    private final OAuthProvider provider;

    public OAuthApiException(final ExceptionCode exceptionCode, OAuthProvider provider) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
        this.provider = provider;
    }

    public OAuthApiException(final ExceptionCode exceptionCode, OAuthProvider provider, String message) {
        this.code = exceptionCode.getCode();
        this.message = message;
        this.provider = provider;
    }
}
