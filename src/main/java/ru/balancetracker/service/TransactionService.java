package ru.balancetracker.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.balancetracker.model.dto.TransactionDTO;
import ru.balancetracker.model.jpa.PeriodicTransaction;
import ru.balancetracker.model.jpa.Transaction;
import ru.balancetracker.model.jpa.TransactionAccount;
import ru.balancetracker.repository.PeriodicTransactionRepository;
import ru.balancetracker.repository.TransactionAccountRepository;
import ru.balancetracker.repository.TransactionRepository;
import ru.balancetracker.security.utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final PeriodicTransactionRepository periodicTransactionRepository;
    private final TransactionAccountRepository transactionAccountRepository;
    private final TransactionAccountService transactionAccountService;

    public Long createTransactionAndSave(TransactionDTO transactionDTO) {
        Transaction savedTransaction = convertAndSave(transactionDTO);
        return savedTransaction.getId();

    }

    /**
     * Method is required to get list of transactions for page filling
     *
     * @param itemsPerPage    how many transactions are displayed per page
     * @param pageNumber      which page is it, but starts from 0
     * @param userId          user, owner of transactions
     * @param transactionDate date, bellow which transactions are required
     */
    public List<Transaction> getTransactionsPerPage(Integer itemsPerPage,
                                                    Integer pageNumber,
                                                    String userId,
                                                    LocalDateTime transactionDate) {

        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by("transactionDate"));

        return repository.findTransactionsForUser(userId, transactionDate, pageable);

    }

    /**
     * Update only changed transaction fields
     *
     * @param transactionDTO dto with changed information
     * @param id             identifier of transaction which will be changed
     */
    public void updateTransaction(TransactionDTO transactionDTO, Long id) {
        Transaction transactionToUpdate = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This transaction does not exist"));

        updateFieldsIfChanged(transactionDTO, transactionToUpdate);
        repository.save(transactionToUpdate);

    }

    /**
     * Set isDeleted true, we don't literally delete transactions from DB
     *
     * @param id identifier of transaction to delete
     */
    public void deleteTransaction(Long id) {
        Transaction transactionToDelete = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This transaction does not exist"));
        transactionToDelete.setDeleted(true);
        repository.save(transactionToDelete);
    }

    public Long createPeriodicTransactionAndSave(TransactionDTO transactionDTO, Long timePeriod) {
        Transaction savedTransaction = convertAndSave(transactionDTO);

        PeriodicTransaction periodicTransaction = new PeriodicTransaction();
        periodicTransaction.setRepeatMetricValue(timePeriod);
        periodicTransaction.setTransaction(savedTransaction);

        periodicTransactionRepository.saveAndFlush(periodicTransaction);

        return savedTransaction.getId();
    }

    private Transaction convertDtoToTransaction(TransactionDTO transactionDTO) {


        TransactionAccount source = transactionAccountRepository.findById(transactionDTO.getSourceId())
                .orElseThrow(() -> new IllegalArgumentException("This transaction account does not exist"));

        TransactionAccount destination = transactionAccountRepository.findById(transactionDTO.getDestinationId())
                .orElseThrow(() -> new IllegalArgumentException("This transaction account does not exist"));
        Double amount = transactionDTO.getAmount();
        String userId = SecurityUtils.getCurrentUser().getId();
        String comment = transactionDTO.getComment();
        LocalDateTime transactionDate = transactionDTO.getTransactionDate();


        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transactionDate);
        transaction.setSource(source);
        transaction.setDestination(destination);
        transaction.setAmount(amount);
        transaction.setUserId(userId);
        transaction.setComment(comment);
        transaction.setDeleted(false);
        transaction.setDisplayed(true);

        return transaction;
    }

    private void updateFieldsIfChanged(TransactionDTO transactionWithUpdateData, Transaction transactionToUpdate) {

        Long dtoSourceId = transactionWithUpdateData.getSourceId();
        Long dtoDestinationId = transactionWithUpdateData.getDestinationId();
        Double dtoAmount = transactionWithUpdateData.getAmount();
        LocalDateTime dtoTransactionDate = transactionWithUpdateData.getTransactionDate();
        String dtoComment = transactionWithUpdateData.getComment();

        if (dtoSourceId != null) {
            TransactionAccount newSource = transactionAccountRepository.findById(dtoSourceId)
                    .orElseThrow(() -> new IllegalArgumentException("This transaction account does not exist"));
            transactionAccountService.checkThatTransactionAccountIsNotDelted(newSource);
            transactionToUpdate.setSource(newSource);
        }
        if (dtoDestinationId != null) {
            TransactionAccount newDestination = transactionAccountRepository.findById(dtoDestinationId)
                    .orElseThrow(() -> new IllegalArgumentException("This transaction account does not exist"));
            transactionAccountService.checkThatTransactionAccountIsNotDelted(newDestination);
            transactionToUpdate.setDestination(newDestination);
        }
        if (dtoAmount != null) {
            transactionToUpdate.setAmount(dtoAmount);
        }
        if (dtoTransactionDate != null) {
            transactionToUpdate.setTransactionDate(dtoTransactionDate);
        }
        if (dtoComment != null) {
            transactionToUpdate.setComment(dtoComment);
        }

    }


    private Transaction convertAndSave(TransactionDTO transactionDTO) {
        Transaction transactionToSave = convertDtoToTransaction(transactionDTO);
        return repository.saveAndFlush(transactionToSave);
    }
}
