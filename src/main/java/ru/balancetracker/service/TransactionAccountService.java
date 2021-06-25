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
        }else {
            throw new BTRestException(MessageCode.USER_DOESNT_HAVE_ACCESS_TO_TRANSACTION_ACCOUNT,
                    "Current user can't delete this transaction account",
                    null);
        }
        }

    public void update(Long id, TransactionAccountDTO transactionAccountDTO){
        TransactionAccount transactionAccountToUpdate = repository.findById(id).get();

        if(checkUserRightsToTransactionAccount(transactionAccountToUpdate)) {
            changeFieldsIfChanged(transactionAccountDTO, transactionAccountToUpdate);
            repository.save(transactionAccountToUpdate);
        } else {
            throw new BTRestException(MessageCode.USER_DOESNT_HAVE_ACCESS_TO_TRANSACTION_ACCOUNT,
                    "Current user can't delete this transaction account",
                    null);
        }
    }

    private TransactionAccount createTransactionAccount(TransactionAccountDTO transactionAccountDTO, AccountType type){
        TransactionAccount transactionAccountToSave = new TransactionAccount();

        transactionAccountToSave.setTransactionAccountType(
                transactionAccountTypeRepository.findByAccountType(type));
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
        if(transactionAccountDTO.getComment() != null && !transactionAccount.getComment().equals(transactionAccountDTO.getComment())){
            transactionAccount.setComment(transactionAccountDTO.getComment());
        }
        if(transactionAccountDTO.getName() != null && !transactionAccount.getName().equals(transactionAccountDTO.getName())){
            transactionAccount.setName(transactionAccountDTO.getName());
        }
        if(transactionAccountDTO.getIcon() != null && !transactionAccount.getIcon().getId().equals(transactionAccountDTO.getIcon())){
            transactionAccount.setIcon(iconRepository.findById(transactionAccountDTO.getIcon()).get());
        }

    }

    private boolean checkUserRightsToTransactionAccount(TransactionAccount transactionAccount){
        String userId = SecurityUtils.getCurrentUser().getId();
        return transactionAccount.getUserId().equals(userId);
    }

    public void checkThatTransactionAccountValidity(@NonNull TransactionAccount transactionAccount){
        if(transactionAccount.isDeleted()){
            throw new BTRestException(MessageCode.THIS_TRANSACTION_ACCOUNT_DOES_NOT_EXIST,
                    "Transaction account which intended to be used marked as deleted",
                    null);
        }
    }


}
