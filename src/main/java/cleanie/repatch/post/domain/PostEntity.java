package cleanie.repatch.post.domain;

import cleanie.repatch.photo.domain.PhotoEntity;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.post.domain.enums.TransactionTypes;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Enumerated(EnumType.STRING)
    private final PostType postType;

    @Enumerated(EnumType.STRING)
    private final FabricType fabricType;

    private final String title;
    private final String unit;
    private final String price;
    private final String content;
    private final Boolean isPublished;

    @Embedded
    private final TransactionTypes transactionTypes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<PhotoEntity> photos;

    @CreatedDate
    private final LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
