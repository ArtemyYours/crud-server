package ru.balancetracker.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_account")
@Entity
public class TransactionAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "transaction_account_type_id", nullable = false)
    private TransactionAccountType transactionAccountType;

    @ManyToOne
    @JoinColumn(name = "icon_id", nullable = false)
    private Icon icon;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "comment")
    private String comment;

    @JsonIgnore
    @OneToMany(mappedBy = "source")
    private List<Transaction> transactionsHereUserAsSource;

    @JsonIgnore
    @OneToMany(mappedBy = "destination")
    private List<Transaction> transactionsHereUserAsDestination;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Transient
    private double deposit;
}
