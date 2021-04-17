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
            "ORDER BY ta.name")
    List<TransactionAccount> findTransactionsForUser(String userId);

}
