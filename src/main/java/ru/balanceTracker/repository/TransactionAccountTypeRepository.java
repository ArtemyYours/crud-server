package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balanceTracker.model.TransactionAccountType;

public interface TransactionAccountTypeRepository extends JpaRepository<TransactionAccountType, Long> {
}
