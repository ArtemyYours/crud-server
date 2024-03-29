package ru.balancetracker.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.balancetracker.exceptions.MessageCode;
import ru.balancetracker.model.exception.BTRestException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private TransactionAccount source;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private TransactionAccount destination;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "is_displayed", columnDefinition = "boolean default false")
    private boolean isDisplayed;



    public void setSource(TransactionAccount source){
        if(source.getTransactionAccountType().getAccountType() == AccountType.OUTCOME){
            throw new BTRestException(MessageCode.OUTCOME_CANNOT_BE_SOURCE,
                    "Transaction account with type outcome cannot be used as a source in transaction",
                    null);
        }
        this.source = source;
    }
    public void setDestination(TransactionAccount destination){
        //We cannot set INCOME transaction account as destination, but we do it for initialising transactions
        if(destination.getTransactionAccountType().getAccountType() == AccountType.INCOME
                && this.getSource() != null
                && this.getSource().getTransactionAccountType().getAccountType() != AccountType.INITIALIZER){
            throw new BTRestException(MessageCode.INCOME_CANNOT_BE_DESTINATION,
                    "Transaction account with type income cannot be used as a source in destination",
                    null);
        }
        this.destination = destination;
    }


}
