package cleanie.repatch.user.domain;

public enum UserType {
    BUYER, SELLER;

    public boolean isBuyer() {
        return this == BUYER;
    }

    public boolean isSeller() {
        return this == SELLER;
    }
}
