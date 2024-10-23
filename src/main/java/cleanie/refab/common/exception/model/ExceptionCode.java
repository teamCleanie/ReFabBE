package cleanie.refab.common.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    // 95xx: 외부 서비스 호출 에러
    DISCORD_WEBHOOK_ERROR(9511, "디스코드 Webhook 호출 중 오류가 발생했습니다."),

    INTERNAL_SERVER_ERROR(9999, "서버 에러가 발생했습니다.");

    private final int code;
    private final String message;
}
