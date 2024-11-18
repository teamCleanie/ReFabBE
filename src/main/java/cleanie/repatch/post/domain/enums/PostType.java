package cleanie.repatch.post.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType {
    BUY("구매", "Buy"),
    SELL("판매", "Sell");

    private final String postKorName;
    private final String postEngName;
}
