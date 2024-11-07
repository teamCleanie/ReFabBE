package cleanie.repatch.post.domain.enums;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
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

    public static TransactionType getTypeByString(String name){
        for (TransactionType type : TransactionType.values()){
            if (type.transactionKorName.equals(name) || type.transactionEngName.equalsIgnoreCase(name)){
                return type;
            }
        }
        throw new BadRequestException(ExceptionCode.INVALID_REQUEST);
    }
}
