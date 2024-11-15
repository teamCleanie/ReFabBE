package cleanie.repatch.common.security.domain;

public enum Authority {
    GUEST, MEMBER;

    public boolean isMember() {
        return this == MEMBER;
    }
}
