package cleanie.repatch.user.domain;

import cleanie.repatch.user.model.OAuthProvider;
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

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private OAuthProvider provider;

    @Column(nullable = false)
    private String socialLoginId;

    private String refreshToken;

    protected User(String name, String imageUrl, OAuthProvider provider, String socialLoginId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.socialLoginId = socialLoginId;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public abstract UserType getUserType();
}
