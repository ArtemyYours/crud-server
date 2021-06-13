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
import ru.balancetracker.security.utils.SecurityUtils;

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
        if(checkUserRightsToTransactionAccount(transactionAccount)) {
            transactionAccount.setDeleted(true);
            repository.save(transactionAccount);
        }
        //TODO: implements common exception class to pass to front
        throw new IllegalArgumentException();
    }

    public void update(Long id, TransactionAccountDTO transactionAccountDTO){
        TransactionAccount transactionAccountToUpdate = repository.findById(id).get();

        if(checkUserRightsToTransactionAccount(transactionAccountToUpdate)) {
            changeFieldsIfChanged(transactionAccountDTO, transactionAccountToUpdate);
            repository.save(transactionAccountToUpdate);
        }

        //TODO: implements common exception class to pass to front
        throw new IllegalArgumentException();
    }

    private TransactionAccount createTransactionAccount(TransactionAccountDTO transactionAccountDTO, AccountType type){
        TransactionAccount transactionAccountToSave = new TransactionAccount();

        transactionAccountToSave.setTransactionAccountType(
                transactionAccountTypeRepository.findByAccountTupe(type.toString()));
        transactionAccountToSave.setComment(transactionAccountDTO.getComment());
        transactionAccountToSave.setIcon(iconRepository.findById(transactionAccountDTO.getIcon()).get());
        transactionAccountToSave.setUserId(SecurityUtils.getCurrentUser().getId());
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
        initialisingTransaction.setUserId(SecurityUtils.getCurrentUser().getId());

        transactionRepository.save(initialisingTransaction);
    }

    private void changeFieldsIfChanged(TransactionAccountDTO transactionAccountDTO,
                                       TransactionAccount transactionAccount){
        if(!transactionAccount.getComment().equals(transactionAccountDTO.getComment()) && transactionAccount.getComment() != null){
            transactionAccount.setComment(transactionAccountDTO.getComment());
        }
        if(!transactionAccount.getName().equals(transactionAccountDTO.getName()) && transactionAccount.getName() != null){
            transactionAccount.setName(transactionAccountDTO.getName());
        }
        if(!transactionAccount.getIcon().getId().equals(transactionAccountDTO.getIcon()) && transactionAccount.getIcon() != null){
            transactionAccount.setIcon(iconRepository.findById(transactionAccountDTO.getIcon()).get());
        }

    }

    private boolean checkUserRightsToTransactionAccount(TransactionAccount transactionAccount){
        String userId = SecurityUtils.getCurrentUser().getId();
        return transactionAccount.getUserId().equals(userId);

    }


}
