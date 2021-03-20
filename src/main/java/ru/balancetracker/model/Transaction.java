package ru.balancetracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_account_id", nullable = false)
    private TransactionAccount source;

    @ManyToOne
    @JoinColumn(name = "transaction_account_id", nullable = false)
    private TransactionAccount destination;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_periodic")
    private boolean isPeriodic;
}
