package cleanie.repatch.common.security.domain;

import cleanie.repatch.user.domain.UserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Accessor {
    private final Long userId;
    private final UserType userType;
    private final Authority authority;

    public static Accessor guest() {
        return new Accessor(0L, null, Authority.GUEST);
    }

    public static Accessor member(Long userId, UserType userType) {
        return new Accessor(userId, userType, Authority.MEMBER);
    }

    public boolean isMember() {
        return authority.isMember();
    }

    public boolean isBuyer() {
        return isMember() && userType.isBuyer();
    }

    public boolean isSeller() {
        return isMember() && userType.isSeller();
    }
}
