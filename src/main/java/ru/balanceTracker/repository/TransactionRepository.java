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

    @Query(value = "SELECT * " +
            "FROM transaction t " +
            "WHERE t.user_id = :userId " +
            "AND t.transaction_date<=:transactionDate " +
            "AND t.is_deleted = false " +
            "AND t.is_displayed = true " +
            "ORDER BY t.transaction_date DESC " +
            "OFFSET :offset " +
            "LIMIT :limit ",
            nativeQuery = true
            )
    List<Transaction> findTransactionsForPage(@Param("userId") String userId,
                                              @Param("transactionDate")LocalDateTime transactionDate,
                                              @Param("offset") Integer offset,
                                              @Param("limit") Integer limit);

}
