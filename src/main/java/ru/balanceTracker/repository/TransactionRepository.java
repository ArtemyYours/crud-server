package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balanceTracker.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


}
