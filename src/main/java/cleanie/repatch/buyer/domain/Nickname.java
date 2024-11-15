package cleanie.repatch.buyer.domain;

import cleanie.repatch.common.exception.BadRequestException;

import static cleanie.repatch.common.exception.model.ExceptionCode.INVALID_USER_NICKNAME;

public class Nickname {

    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 12;

    private final String nickname;

    public Nickname(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    private void validate(String nickname) {
        if (nickname.contains(" ") || containsSpecialCharacter(nickname) || isInvalidNicknameLength(nickname)) {
            throw new BadRequestException(INVALID_USER_NICKNAME);
        }
    }

    private boolean containsSpecialCharacter(String nickname) {
        return nickname.matches(".*[^가-힣a-zA-Z0-9 ].*");
    }

    private boolean isInvalidNicknameLength(String nickname) {
        return nickname.length() < MIN_NICKNAME_LENGTH || nickname.length() > MAX_NICKNAME_LENGTH;
    }
}
