package ru.balanceTracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balanceTracker.model.dto.TransactionDTO;
import ru.balanceTracker.model.jpa.Transaction;
import ru.balanceTracker.model.jpa.TransactionAccount;
import ru.balanceTracker.repository.TransactionAccountRepository;
import ru.balanceTracker.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionAccountRepository transactionAccountRepository;

    public Long createTransactionAndSave(TransactionDTO transactionDTO){
        Transaction transactionToSave = convertDtoToTransaction(transactionDTO);
        Transaction savedTransaction = repository.saveAndFlush(transactionToSave);

        return savedTransaction.getId();

    }

    /** Method is required to get list of transactions for page filling
     *
     * @param itemsPerPage how many transactions are displayed per page
     * @param pageNumber which page is it
     * @param userId user, owner of transactions
     * @param transactionDate date, bellow which transactions are required
     * @return
     */
    public List<Transaction> getTransactionsPerPage(Integer itemsPerPage,
                                                    Integer pageNumber,
                                                    String userId,
                                                    LocalDateTime transactionDate){
        Integer offset = (pageNumber - 1) * itemsPerPage;
        Integer limit = itemsPerPage;

        List<Transaction> transactions = repository.findTransactionsForPage(userId, transactionDate, offset, limit);

        return transactions;

    }

    /**Update only changed transaction fields
     *
     * @param transactionDTO dto with changed information
     * @param id identifier of transaction which will be changed
     */
    public void updateTransaction(TransactionDTO transactionDTO, Long id){
        Transaction transactionToUpdate = repository.findById(id).get();
        Transaction transactionWithUpdateData = convertDtoToTransaction(transactionDTO);

        updateFieldsIfChanged(transactionWithUpdateData, transactionToUpdate);

        repository.save(transactionToUpdate);
    }

    /**Set isDeleted true, we don't literally delete transactions from DB
     *
     * @param id identifier of transaction to delete
     */
    public void deleteTransaction(Long id){
        Transaction transactionToDelete = repository.findById(id).get();
        transactionToDelete.setDeleted(true);
        repository.save(transactionToDelete);
    }

    private Transaction convertDtoToTransaction(TransactionDTO transactionDTO){


        TransactionAccount source = transactionAccountRepository.findById(transactionDTO.getSourceId()).get();
        TransactionAccount destination = transactionAccountRepository.findById(transactionDTO.getDestinationId()).get();
        Double amount = transactionDTO.getAmount();
        String userId = transactionDTO.getUserId();
        String comment = transactionDTO.getComment();
        LocalDateTime transactionDate = transactionDTO.getTransactionDate();

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transactionDate);
        transaction.setSource(source);
        transaction.setDestination(destination);
        transaction.setAmount(amount);
        transaction.setUserId(userId);
        transaction.setComment(comment);

        return transaction;
    }

    private Transaction updateFieldsIfChanged(Transaction transactionWithUpdateData, Transaction transactionToUpdate){

        if(!transactionToUpdate.getSource().equals(transactionWithUpdateData.getSource())){
            transactionToUpdate.setSource(transactionWithUpdateData.getSource());
        }
        if(!transactionToUpdate.getDestination().equals(transactionWithUpdateData.getDestination())){
            transactionToUpdate.setDestination(transactionWithUpdateData.getDestination());
        }
        if(!transactionToUpdate.getAmount().equals(transactionWithUpdateData.getAmount())){
            transactionToUpdate.setAmount(transactionWithUpdateData.getAmount());
        }
        if(!transactionToUpdate.getAmount().equals(transactionWithUpdateData.getAmount())){
            transactionToUpdate.setAmount(transactionWithUpdateData.getAmount());
        }
        if(!transactionToUpdate.getTransactionDate().equals(transactionWithUpdateData.getTransactionDate())){
            transactionToUpdate.setTransactionDate(transactionWithUpdateData.getTransactionDate());
        }
        if(!transactionToUpdate.getComment().equals(transactionWithUpdateData.getComment())){
            transactionToUpdate.setComment(transactionWithUpdateData.getComment());
        }
        return transactionToUpdate;
    }
}
