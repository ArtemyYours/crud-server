package ru.balanceTracker.model;

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
