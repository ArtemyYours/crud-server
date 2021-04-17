package ru.balanceTracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balanceTracker.model.dto.TransactionDTO;
import ru.balanceTracker.model.jpa.Transaction;
import ru.balanceTracker.repository.TransactionRepository;
import ru.balanceTracker.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService service;


    @PostMapping("/create")
    public Long createTransaction(@RequestBody TransactionDTO transactionDTO){
        return service.createTransactionAndSave(transactionDTO);
    }

    @GetMapping("/{userId}/{itemsPerPage}/{pageNumber}/transactions-for-user")
    public List<Transaction> getTransactionsForUser(@PathVariable String userId,
                                                    @PathVariable Integer itemsPerPage,
                                                    @PathVariable Integer pageNumber,
                                                    @RequestBody LocalDateTime transactionDate){
        return service.getTransactionsPerPage(itemsPerPage, pageNumber, userId, transactionDate);
    }

    @DeleteMapping("/{transactionId}/delete")
    public void deleteTransaction(@PathVariable Long transactionId){
        service.deleteTransaction(transactionId);
    }

    @PutMapping("/{transactionId}/update")
    public void update(@PathVariable Long transactionId,
                       @RequestBody TransactionDTO transactionDTO){
        service.updateTransaction(transactionDTO, transactionId);
    }


}
