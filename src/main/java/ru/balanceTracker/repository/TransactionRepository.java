package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.balanceTracker.model.jpa.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t " +
            "FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "AND t.transactionDate<=:transactionDate " +
            "AND t.isDeleted = false " +
            "ORDER BY t.transactionDate DESC " +
            "LIMIT :limit " +
            "OFFSET :offset ")
    List<Transaction> findTransactionsForPage(@Param("userId") String userId,
                                              @Param("transactionDate")LocalDateTime transactionDate,
                                              @Param("offset") Integer offset,
                                              @Param("limit") Integer limit);

    void deleteById(Long id);
}
