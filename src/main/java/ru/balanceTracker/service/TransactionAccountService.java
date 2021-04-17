package ru.balanceTracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balanceTracker.model.jpa.TransactionAccount;
import ru.balanceTracker.repository.TransactionAccountRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionAccountService {
    private TransactionAccountRepository repository;

    public List<TransactionAccount> getTransactionAccountsForUser(String userId){
        return repository.findTransactionsForUser(userId);

    }

    public TransactionAccount getSumForDeposit(TransactionAccount transactionAccount)
}
