package cleanie.repatch.post.domain.enums;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Embeddable
@RequiredArgsConstructor
public class TransactionTypes {

    @ElementCollection(targetClass = TransactionType.class)
    @CollectionTable(name = "transaction_type", joinColumns = @JoinColumn(name = "post_id"))
    @Enumerated(EnumType.STRING)
    private final Set<TransactionType> transactionTypes;

    public TransactionTypes() {
        this.transactionTypes = new HashSet<>();
    }

    public TransactionTypes updateTransactionTypes(Set<TransactionType> updates) {
        this.transactionTypes.clear();
        this.transactionTypes.addAll(updates);

        return this;
    }

    public Set<String> createStringSet(Set<TransactionType> transactions) {
        return transactions.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
