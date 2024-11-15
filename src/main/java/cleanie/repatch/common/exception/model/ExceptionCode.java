package cleanie.repatch.common.exception.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    // 11xx: 사용자 관련 에러
    INVALID_AUTHORITY(1101, "권한이 없습니다."),

    USER_NOT_FOUND(1201, "사용자를 찾을 수 없습니다."),

    INVALID_USER_BUSINESS_NAME(1301, "올바르지 않은 상호명 입니다."),


    // 95xx: 외부 서비스 호출 에러
    DISCORD_WEBHOOK_ERROR(9511, "디스코드 Webhook 호출 중 오류가 발생했습니다."),
    OAUTH_API_ERROR(9521, "OAuth API 호출 중 오류가 발생했습니다."),
    INVALID_OAUTH_TOKEN(9522, "올바르지 않은 OAuth 토큰입니다."),

    INTERNAL_SERVER_ERROR(9999, "서버 에러가 발생했습니다.");

    private final int code;
    private final String message;
}
