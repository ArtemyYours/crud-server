package ru.balancetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.balancetracker.model.jpa.TransactionAccount;

import java.util.List;

@Repository
public interface TransactionAccountRepository extends JpaRepository<TransactionAccount, Long> {

    @Query("SELECT ta " +
            "FROM TransactionAccount ta " +
            "WHERE ta.userId = :userId " +
            "AND ta.isDeleted = false " +
            "AND ta.transactionAccountType.id = " +
            "(SELECT tat.id FROM TransactionAccountType tat WHERE tat.accountType = 'PURSE') " +
            "ORDER BY ta.name")
    List<TransactionAccount> findPursesForUser(String userId);

    @Query("SELECT ta " +
            "FROM TransactionAccount ta " +
            "WHERE ta.userId = :userId " +
            "AND ta.isDeleted = false " +
            "AND ta.transactionAccountType.id = " +
            "(SELECT tat.id FROM TransactionAccountType tat WHERE tat.accountType = 'OUTCOME') " +
            "ORDER BY ta.name")
    List<TransactionAccount> findOutcomeForUser(String userId);

    @Query("SELECT ta " +
            "FROM TransactionAccount ta " +
            "WHERE ta.userId = :userId " +
            "AND ta.isDeleted = false " +
            "AND ta.transactionAccountType.id = " +
            "(SELECT tat.id FROM TransactionAccountType tat WHERE tat.accountType = 'INCOME') " +
            "ORDER BY ta.name")
    List<TransactionAccount> findIncomeForUser(String userId);

    @Query(value = "SELECT ta FROM TransactionAccount ta " +
            "WHERE ta.transactionAccountType = (SELECT tat FROM TransactionAccountType tat WHERE tat.accountType = 'INITIALIZER')")
    TransactionAccount getInitializingIncome();

    //TODO: find a way to do it with a jpql query
    @Query(value = "SELECT tad.deposit " +
            "FROM transaction_account_deposit tad " +
            "WHERE tad.transaction_account_id = :transactionAccountId", nativeQuery = true)
    Double getDepositForAccount(Long transactionAccountId);

}
