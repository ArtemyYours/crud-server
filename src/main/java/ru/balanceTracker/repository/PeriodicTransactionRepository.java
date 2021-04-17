package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.balanceTracker.model.jpa.PeriodicTransaction;

@Repository
public interface PeriodicTransactionRepository extends JpaRepository<PeriodicTransaction, Long> {
}
