package cleanie.repatch.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private String providerId;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private UserType userType;

    protected User(String email, String name, String imageUrl, String providerId) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.providerId = providerId;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public abstract UserType getUserType();
}
