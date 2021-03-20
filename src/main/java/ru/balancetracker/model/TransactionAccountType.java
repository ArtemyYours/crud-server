package ru.balancetracker.model;

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
@Entity
public class TransactionAccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_money_source")
    private boolean isMoneySource;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_money_destination")
    private boolean isMoneyDestination;

    @Column(name = "comment")
    private String comment;

    @JsonIgnore
    @OneToMany(mappedBy = "transaction_account_type")
    private List<TransactionAccount> transactionAccounts;


}
