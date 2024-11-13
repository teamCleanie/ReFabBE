package cleanie.repatch.post.domain;

import cleanie.repatch.common.domain.BaseEntity;
import cleanie.repatch.photo.domain.Photo;
import cleanie.repatch.post.domain.enums.FabricType;
import cleanie.repatch.post.domain.enums.PostType;
import cleanie.repatch.post.domain.enums.TransactionTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private FabricType fabricType;

    private String title;
    private String unit;
    private String price;
    private String content;
    private Boolean isPublished;

    @Embedded
    private TransactionTypes transactionTypes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private List<Photo> photos;
}
