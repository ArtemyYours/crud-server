package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balanceTracker.model.PeriodicTransaction;

public interface PeriodicTransactionRepository extends JpaRepository<PeriodicTransaction, Long> {
}
