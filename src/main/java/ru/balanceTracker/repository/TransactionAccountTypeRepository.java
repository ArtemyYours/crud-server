package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.balanceTracker.model.jpa.TransactionAccountType;

@Repository
public interface TransactionAccountTypeRepository extends JpaRepository<TransactionAccountType, Long> {
}
