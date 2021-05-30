package ru.balanceTracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balanceTracker.model.dto.TransactionDTO;
import ru.balanceTracker.model.jpa.Transaction;
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

    @GetMapping("/transactions-for-user/{userId}/{itemsPerPage}/{pageNumber}")
    public List<Transaction> getTransactionsForUser(@PathVariable String userId,
                                                    @PathVariable Integer itemsPerPage,
                                                    @PathVariable Integer pageNumber,
                                                    @RequestBody LocalDateTime transactionDate){
        return service.getTransactionsPerPage(itemsPerPage, pageNumber, userId, transactionDate);
    }

    @DeleteMapping("/delete/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId){
        service.deleteTransaction(transactionId);
    }

    @PutMapping("/update/{transactionId}")
    public void update(@PathVariable Long transactionId,
                       @RequestBody TransactionDTO transactionDTO){
        service.updateTransaction(transactionDTO, transactionId);
    }

    @PostMapping("/create-periodic/{timePeriod}")
    public Long createPeriodicTransaction(@PathVariable Long timePeriod,
            @RequestBody TransactionDTO transactionDTO){
        return service.createPeriodicTransactionAndSave(transactionDTO, timePeriod);
    }




}
