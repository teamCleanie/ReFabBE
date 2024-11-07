package cleanie.repatch.post.domain.enums;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType {
    BUY("구매", "Buy"),
    SELL("판매", "Sell");

    private final String postKorName;
    private final String postEngName;

    public static PostType getTypeByString(String trade){
        for (PostType postType : PostType.values()){
            if (postType.postKorName.equals(trade) || postType.postEngName.equalsIgnoreCase(trade)){
                return postType;
            }
        }
        throw new BadRequestException(ExceptionCode.INVALID_REQUEST);
    }
}
