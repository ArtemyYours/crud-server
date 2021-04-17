package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balanceTracker.model.TransactionAccount;

public interface TransactionAccountRepository extends JpaRepository<TransactionAccount, Long> {

}
