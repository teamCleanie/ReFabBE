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

    // 사진 관련 오류
    FILE_TYPE_NOT_SUPPORTED(2001,"지원하는 이미지 형식이 아닙니다."),
    FILE_NOT_FOUND(2002, "파일이 존재하지 않습니다."),

    // 게시글 관련 오류
    POST_NOT_FOUND(2101, "게시글을 찾을 수 없습니다."),
    INVALID_POST_REQUEST(2102, "게시글 요청이 올바르지 않습니다."),
    INVALID_VALUE(2103, "올바르지 않은 선택값입니다."),
    DRAFT_NOT_FOUND(2110, "임시저장된 글을 찾을 수 없습니다."),
    
    // 95xx: 외부 서비스 호출 에러
    DISCORD_WEBHOOK_ERROR(9511, "디스코드 Webhook 호출 중 오류가 발생했습니다."),
    OAUTH_API_ERROR(9521, "OAuth API 호출 중 오류가 발생했습니다."),
    INVALID_OAUTH_TOKEN(9522, "올바르지 않은 OAuth 토큰입니다."),

    INTERNAL_SERVER_ERROR(9999, "서버 에러가 발생했습니다.");

    private final int code;
    private final String message;
}
