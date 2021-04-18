package ru.balanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.balanceTracker.model.jpa.AccountType;
import ru.balanceTracker.model.jpa.TransactionAccountType;

@Repository
public interface TransactionAccountTypeRepository extends JpaRepository<TransactionAccountType, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM public.transaction_account_type tat WHERE tat.account_type = :type ")
    TransactionAccountType findByAccountTupe(@Param("type") String type);

}
