package cleanie.refab.common.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public record ExceptionResponse(int code, String message) {
}
