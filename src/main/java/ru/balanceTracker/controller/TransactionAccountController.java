package ru.balanceTracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.balanceTracker.model.jpa.TransactionAccount;
import ru.balanceTracker.service.TransactionAccountService;

import java.util.List;

@RestController
@RequestMapping("/transaction-account")
@AllArgsConstructor
public class TransactionAccountController {
    private TransactionAccountService service;

    @GetMapping("/{userId}/get-transaction-accounts")
    public List<TransactionAccount> getTransactionAccountsForUser(@PathVariable String userId){
        return service.getTransactionAccountsForUser(userId);
    }

    @Delete
}
