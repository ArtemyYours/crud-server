package ru.balancetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balancetracker.model.dto.TransactionDTO;
import ru.balancetracker.model.jpa.Transaction;
import ru.balancetracker.security.SecurityConstants;
import ru.balancetracker.security.utils.SecurityUtils;
import ru.balancetracker.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create")
    public Long createTransaction(@RequestBody TransactionDTO transactionDTO){
        return service.createTransactionAndSave(transactionDTO);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PutMapping("/transactions-for-user/{itemsPerPage}/{pageNumber}")
    public List<Transaction> getTransactionsForUser(@PathVariable Integer itemsPerPage,
                                                    @PathVariable Integer pageNumber,
                                                    @RequestBody LocalDateTime transactionDate){
        String userId = SecurityUtils.getCurrentUser().getId();
        return service.getTransactionsPerPage(itemsPerPage, pageNumber, userId, transactionDate);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @DeleteMapping("/delete/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId){
        service.deleteTransaction(transactionId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PutMapping("/update/{transactionId}")
    public void update(@PathVariable Long transactionId,
                       @RequestBody TransactionDTO transactionDTO){
        service.updateTransaction(transactionDTO, transactionId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-periodic/{timePeriod}")
    public Long createPeriodicTransaction(@PathVariable Long timePeriod,
            @RequestBody TransactionDTO transactionDTO){
        return service.createPeriodicTransactionAndSave(transactionDTO, timePeriod);
    }




}
