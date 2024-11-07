package cleanie.repatch.post.domain.enums;

import cleanie.repatch.common.exception.BadRequestException;
import cleanie.repatch.common.exception.model.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FabricType {

    COTTON100("Cotton", "면100"),
    COTTON_TWILL("Cotton Twill", "면 트윌"),
    CVC("CVC","면 혼방"),
    LINEN("Linen","린넨"),
    POLYESTER("Polyester","폴리"),
    WOOL("Wool","울"),
    OXFORD("Oxford","옥스포드"),
    CIRCULAR_KNIT("Circular Knit","다이마루"),
    NYLON("Nylon","나일론"),
    FUNCTIONAL_FABRIC("Functional Fabric","기능성 원단"),
    DENIM("Denim","데님"),
    SPECIAL_FABRIC("Special Fabric","특수 원단"),
    LACE("Lace", "레이스"),
    ETC("ETC","기타 항목")
    ;

    private final String fabricEngName;
    private final String fabricKorName;

    public static FabricType getTypeByString(String name) {
        for (FabricType type : FabricType.values()) {
            if (type.fabricEngName.equalsIgnoreCase(name) || type.fabricKorName.equals(name)) {
                return type;
            }
        }
        throw new BadRequestException(ExceptionCode.INVALID_REQUEST);
    }
}