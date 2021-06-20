package ru.balancetracker.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.balancetracker.model.jpa.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT t " +
            "FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "AND t.transactionDate <= :transactionDate " +
            "AND t.isDeleted = false " +
            "AND t.isDisplayed = true " +
            "ORDER BY t.transactionDate DESC " +
            "OFFSET :offset " +
            "LIMIT :limit "
            )
    List<Transaction> findTransactionsForPage(@Param("userId") String userId,
                                              @Param("transactionDate")LocalDateTime transactionDate,
                                              @Param("offset") Integer offset,
                                              @Param("limit") Integer limit);


    @Query(value = "SELECT t " +
            "FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "AND t.transactionDate <= :transactionDate " +
            "AND t.isDeleted = false " +
            "AND t.isDisplayed = true "
    )
    List<Transaction> findTransactionsForUser(@Param("userId") String userId,
                                              @Param("transactionDate") LocalDateTime transactionDate,
                                              Pageable pageable);

}
