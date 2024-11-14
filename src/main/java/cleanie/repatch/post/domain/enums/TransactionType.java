package cleanie.repatch.post.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    DIRECT("직거래", "Direct"),
    DELIVERY("택배", "Delivery"),
    ETC("기타", "ETC");

    private final String transactionKorName;
    private final String transactionEngName;
}
