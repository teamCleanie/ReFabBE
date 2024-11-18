package cleanie.repatch.draft.domain.enums;

import cleanie.repatch.post.domain.enums.TransactionType;
import cleanie.repatch.post.domain.enums.TransactionTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Embeddable
@RequiredArgsConstructor
public class DraftTransactionTypes {

    @ElementCollection(targetClass = DraftTransactionType.class)
    @CollectionTable(name = "draft_transaction_type", joinColumns = @JoinColumn(name = "draft_id"))
    @Enumerated(EnumType.STRING)
    private final Set<DraftTransactionType> draftTransactionTypes;

    public DraftTransactionTypes() {
        this.draftTransactionTypes = new HashSet<>();
    }

    public DraftTransactionTypes updateDraftTransactionTypes(Set<DraftTransactionType> updates) {
        this.draftTransactionTypes.clear();
        this.draftTransactionTypes.addAll(updates);

        return this;
    }

    public Set<String> createStringSet(Set<DraftTransactionType> transactions) {
        return transactions.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    public TransactionTypes toTransactionTypes() {
        Set<TransactionType> transactionTypeSet = this.draftTransactionTypes.stream()
                .map(draftTransactionType -> TransactionType.valueOf(draftTransactionType.name()))
                .collect(Collectors.toSet());

        return new TransactionTypes(transactionTypeSet);
    }
}
