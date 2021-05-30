package ru.balancetracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balancetracker.model.dto.TransactionAccountDTO;
import ru.balancetracker.model.jpa.AccountType;
import ru.balancetracker.model.jpa.Transaction;
import ru.balancetracker.model.jpa.TransactionAccount;
import ru.balancetracker.repository.IconRepository;
import ru.balancetracker.repository.TransactionAccountRepository;
import ru.balancetracker.repository.TransactionAccountTypeRepository;
import ru.balancetracker.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionAccountService {
    private TransactionAccountRepository repository;
    private TransactionAccountTypeRepository transactionAccountTypeRepository;
    private IconRepository iconRepository;
    private TransactionRepository transactionRepository;

    public List<TransactionAccount> getPursesForUser(String userId){
        List<TransactionAccount> userAccounts = repository.findPursesForUser(userId);
        userAccounts.forEach(account -> account.setDeposit(repository.getDepositForAccount(account.getId())));
        return userAccounts;
    }

    public List<TransactionAccount> getOutcomeForUser(String userId){
        List<TransactionAccount> userAccounts = repository.findOutcomeForUser(userId);
        return userAccounts;
    }

    public List<TransactionAccount> getIncomeForUser(String userId){
        List<TransactionAccount> userAccounts = repository.findIncomeForUser(userId);
        return userAccounts;
    }

    public Long createPurse(TransactionAccountDTO transactionAccountDTO){
        return createTransactionAccount(transactionAccountDTO, AccountType.PURSE).getId();
    }

    public Long createIncome(TransactionAccountDTO transactionAccountDTO){
        return createTransactionAccount(transactionAccountDTO, AccountType.INCOME).getId();
    }

    public Long createOutcome(TransactionAccountDTO transactionAccountDTO){
        return createTransactionAccount(transactionAccountDTO, AccountType.OUTCOME).getId();
    }

    public void deleteTransactionAccount(Long id){
        TransactionAccount transactionAccount = repository.findById(id).get();
        transactionAccount.setDeleted(true);
        repository.save(transactionAccount);
    }

    public void update(Long id, TransactionAccountDTO transactionAccountDTO){
        TransactionAccount transactionAccountToUpdate = repository.findById(id).get();
        changeFieldsIfChanged(transactionAccountDTO, transactionAccountToUpdate);
        repository.save(transactionAccountToUpdate);

    }

    private TransactionAccount createTransactionAccount(TransactionAccountDTO transactionAccountDTO, AccountType type){
        TransactionAccount transactionAccountToSave = new TransactionAccount();

        transactionAccountToSave.setTransactionAccountType(
                transactionAccountTypeRepository.findByAccountTupe(type.toString()));
        transactionAccountToSave.setComment(transactionAccountDTO.getComment());
        transactionAccountToSave.setIcon(iconRepository.findById(transactionAccountDTO.getIcon()).get());
        transactionAccountToSave.setUserId(transactionAccountDTO.getUserId());
        transactionAccountToSave.setName(transactionAccountDTO.getName());
        TransactionAccount savedAccount = repository.saveAndFlush(transactionAccountToSave);

        if(transactionAccountDTO.getDeposit() > 0d){
            depositInitialValueForPurse(savedAccount, transactionAccountDTO.getDeposit());
        }
        return savedAccount;
    }

    private void depositInitialValueForPurse(TransactionAccount savedTransactionAccount, Double value){
        Transaction initialisingTransaction = new Transaction();

        initialisingTransaction.setAmount(value);
        initialisingTransaction.setDestination(savedTransactionAccount);
        initialisingTransaction.setSource(repository.getInitializingIncome());
        initialisingTransaction.setDisplayed(false);
        initialisingTransaction.setTransactionDate(LocalDateTime.now());
        initialisingTransaction.setUserId(savedTransactionAccount.getUserId());

        transactionRepository.save(initialisingTransaction);
    }

    private void changeFieldsIfChanged(TransactionAccountDTO transactionAccountDTO,
                                       TransactionAccount transactionAccount){
        if(!transactionAccount.getComment().equals(transactionAccountDTO.getComment())){
            transactionAccount.setComment(transactionAccountDTO.getComment());
        }
        if(!transactionAccount.getName().equals(transactionAccountDTO.getName())){
            transactionAccount.setName(transactionAccountDTO.getName());
        }
        if(!transactionAccount.getIcon().getId().equals(transactionAccountDTO.getIcon())){
            transactionAccount.setIcon(iconRepository.findById(transactionAccountDTO.getIcon()).get());
        }

    }


}
