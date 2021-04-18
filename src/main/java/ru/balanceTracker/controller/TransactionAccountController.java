package ru.balanceTracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balanceTracker.model.dto.TransactionAccountDTO;
import ru.balanceTracker.model.jpa.TransactionAccount;
import ru.balanceTracker.service.TransactionAccountService;

import java.util.List;

@RestController
@RequestMapping("/transaction-account")
@AllArgsConstructor
public class TransactionAccountController {
    private TransactionAccountService service;

    @PostMapping("/create-purse")
    public Long createPurse(TransactionAccountDTO transactionAccountDTO){
        return service.createPurse(transactionAccountDTO);
    }

    @PostMapping("/create-outcome")
    public Long createOutcome(TransactionAccountDTO transactionAccountDTO){
        return service.createOutcome(transactionAccountDTO);
    }

    @PostMapping("/create-income")
    public Long createIncome(TransactionAccountDTO transactionAccountDTO){
        return service.createIncome(transactionAccountDTO);
    }

    @GetMapping("/{userId}/purses")
    public List<TransactionAccount> getPursesForUser(@PathVariable String userId){
        return service.getPursesForUser(userId);
    }

    @GetMapping("/{userId}/outcome")
    public List<TransactionAccount> getOutcomesForUser(@PathVariable String userId){
        return service.getOutcomeForUser(userId);
    }

    @GetMapping("/{userId}/income")
    public List<TransactionAccount> getIncomesForUser(@PathVariable String userId){
        return service.getIncomeForUser(userId);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable Long id){
        service.deleteTransactionAccount(id);
    }

    @PutMapping("/{transactionAccountId}/update")
    public void update(@PathVariable Long transactionAccountId,
                       @RequestBody TransactionAccountDTO transactionDTO){
        service.update(transactionAccountId, transactionDTO);
    }

}
