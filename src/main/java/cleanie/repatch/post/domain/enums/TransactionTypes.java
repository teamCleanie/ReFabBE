package cleanie.repatch.post.domain.enums;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Embeddable
@RequiredArgsConstructor
public class TransactionTypes {

    @ElementCollection(targetClass = TransactionType.class)
    @CollectionTable(name = "transaction_type", joinColumns = @JoinColumn(name = "post_id"))
    @Enumerated(EnumType.STRING)
    private final Set<TransactionType> transactionTypes;

    public static Set<String> getStringFromTransactionTypes(TransactionTypes transactionTypes){
        Set<String> types = new HashSet<>();
        for (TransactionType transactionType : transactionTypes.transactionTypes){
            types.add(transactionType.getTransactionKorName());
        }

        return types;
    }

    public static TransactionTypes createTransactionTypesFromString(Set<String> transactions){
        Set<TransactionType> types = new HashSet<>();
        for (String transactionType : transactions){
            types.add(TransactionType.getTypeByString(transactionType));
        }

        return new TransactionTypes(types);
    }

    public static TransactionTypes updateTransactionTypesWithString(
            TransactionTypes transactions, Set<String> transactionTypes){
        Set<TransactionType> types = transactions.transactionTypes;
        types.clear(); // set 비우고 수정된 set

        for (String transactionType : transactionTypes){
            types.add(TransactionType.getTypeByString(transactionType));
        }

        return transactions;
    }
}
