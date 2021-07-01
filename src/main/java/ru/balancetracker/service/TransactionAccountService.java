package ru.balancetracker.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.balancetracker.exceptions.MessageCode;
import ru.balancetracker.model.dto.TransactionAccountDTO;
import ru.balancetracker.model.exception.BTRestException;
import ru.balancetracker.model.jpa.AccountType;
import ru.balancetracker.model.jpa.Transaction;
import ru.balancetracker.model.jpa.TransactionAccount;
import ru.balancetracker.repository.IconRepository;
import ru.balancetracker.repository.TransactionAccountRepository;
import ru.balancetracker.repository.TransactionAccountTypeRepository;
import ru.balancetracker.repository.TransactionRepository;
import ru.balancetracker.security.utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionAccountService {
    private final TransactionAccountRepository repository;
    private final TransactionAccountTypeRepository transactionAccountTypeRepository;
    private final IconRepository iconRepository;
    private final TransactionRepository transactionRepository;

    public List<TransactionAccount> getPursesForUser(String userId) {
        List<TransactionAccount> userAccounts = repository.findPursesForUser(userId);
        userAccounts.forEach(account -> account.setDeposit(repository.getDepositForAccount(account.getId())));
        return userAccounts;
    }

    public List<TransactionAccount> getOutcomeForUser(String userId) {
        List<TransactionAccount> userAccounts = repository.findOutcomeForUser(userId);
        userAccounts.forEach(account -> account.setDeposit(repository.getDepositForAccount(account.getId())));
        return userAccounts;
    }

    public List<TransactionAccount> getIncomeForUser(String userId) {
        List<TransactionAccount> userAccounts = repository.findIncomeForUser(userId);
        userAccounts.forEach(account -> account.setDeposit(repository.getDepositForAccount(account.getId())));
        return userAccounts;
    }

    public Long createPurse(TransactionAccountDTO transactionAccountDTO) {
        return createTransactionAccount(transactionAccountDTO, AccountType.PURSE).getId();
    }

    public Long createIncome(TransactionAccountDTO transactionAccountDTO) {
        return createTransactionAccount(transactionAccountDTO, AccountType.INCOME).getId();
    }

    public Long createOutcome(TransactionAccountDTO transactionAccountDTO) {
        return createTransactionAccount(transactionAccountDTO, AccountType.OUTCOME).getId();
    }

    public void deleteTransactionAccount(Long id) {
        TransactionAccount transactionAccount = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This transaction does not exist"));
        transactionAccount.setDeleted(true);
        repository.save(transactionAccount);
    }

    public void update(Long id, TransactionAccountDTO transactionAccountDTO) {
        TransactionAccount transactionAccountToUpdate = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This transaction account doe not exist"));

        changeFieldsIfChanged(transactionAccountDTO, transactionAccountToUpdate);
        repository.save(transactionAccountToUpdate);

    }

    private TransactionAccount createTransactionAccount(TransactionAccountDTO transactionAccountDTO, AccountType type) {
        TransactionAccount transactionAccountToSave = new TransactionAccount();

        transactionAccountToSave.setTransactionAccountType(
                transactionAccountTypeRepository.findByAccountType(type));
        transactionAccountToSave.setComment(transactionAccountDTO.getComment());
        transactionAccountToSave.setIcon(iconRepository.findById(transactionAccountDTO.getIcon())
                .orElseThrow(() -> new IllegalArgumentException("This icon does not exist")));
        transactionAccountToSave.setUserId(SecurityUtils.getCurrentUser().getId());
        transactionAccountToSave.setName(transactionAccountDTO.getName());
        TransactionAccount savedAccount = repository.saveAndFlush(transactionAccountToSave);

        if (transactionAccountDTO.getDeposit() > 0d) {
            depositInitialValueForPurse(savedAccount, transactionAccountDTO.getDeposit());
        }
        return savedAccount;
    }

    private void depositInitialValueForPurse(TransactionAccount savedTransactionAccount, Double value) {
        Transaction initialisingTransaction = new Transaction();

        initialisingTransaction.setAmount(value);
        initialisingTransaction.setSource(repository.getInitializingIncome());
        initialisingTransaction.setDestination(savedTransactionAccount);
        initialisingTransaction.setDisplayed(false);
        initialisingTransaction.setTransactionDate(LocalDateTime.now());
        initialisingTransaction.setUserId(SecurityUtils.getCurrentUser().getId());

        transactionRepository.save(initialisingTransaction);
    }

    private void changeFieldsIfChanged(TransactionAccountDTO transactionAccountDTO,
                                       TransactionAccount transactionAccount) {
        String dtoComment = transactionAccountDTO.getComment();
        String dtoName = transactionAccountDTO.getName();
        Long dtoIconId = transactionAccountDTO.getIcon();

        if (dtoComment != null) {
            transactionAccount.setComment(transactionAccountDTO.getComment());
        }
        if (dtoName != null) {
            transactionAccount.setName(transactionAccountDTO.getName());
        }
        if (dtoIconId != null) {
            transactionAccount.setIcon(iconRepository.findById(dtoIconId)
                    .orElseThrow(() -> new IllegalArgumentException("This icon does not exist")));
        }

    }

    public void checkThatTransactionAccountIsNotDelted(@NonNull TransactionAccount transactionAccount) {
        if (transactionAccount.isDeleted()) {
            throw new BTRestException(MessageCode.THIS_TRANSACTION_ACCOUNT_DOES_NOT_EXIST,
                    "Transaction account which intended to be used marked as deleted",
                    null);
        }
    }


}
