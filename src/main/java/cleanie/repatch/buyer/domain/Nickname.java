package cleanie.repatch.buyer.domain;

import cleanie.repatch.common.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static cleanie.repatch.common.exception.model.ExceptionCode.INVALID_USER_NICKNAME;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
public class Nickname {

    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 12;

    @Column(name = "display_name")
    private final String value;

    public Nickname(String nickname) {
        validate(nickname);
        this.value = nickname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nickname nickname1 = (Nickname) o;
        return Objects.equals(value, nickname1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
