package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.balanceTracker.model.jpa.TransactionAccount;

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

    @Query(value = "SELECT * FROM public.transaction_account ta " +
            "WHERE ta.transaction_account_type_id = 4;", nativeQuery = true)
    TransactionAccount getInitializingIncome();

    @Query(value = "SELECT 9999.00", nativeQuery = true)
    Double getDepositForAccount(Long transactionAccountId);

}
