package ru.balancetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.balancetracker.model.jpa.AccountType;
import ru.balancetracker.model.jpa.TransactionAccountType;

@Repository
public interface TransactionAccountTypeRepository extends JpaRepository<TransactionAccountType, Long> {

    @Query(value = "SELECT tat FROM TransactionAccountType tat WHERE tat.accountType = :type ")
    TransactionAccountType findByAccountType(AccountType type);

}
