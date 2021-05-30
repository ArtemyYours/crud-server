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
@Table(name = "transaction_account_type")
@Entity
public class TransactionAccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transactionAccountType")
    private List<TransactionAccount> transactionAccounts;


}
