package ru.balancetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balancetracker.model.dto.TransactionAccountDTO;
import ru.balancetracker.model.jpa.TransactionAccount;
import ru.balancetracker.security.SecurityConstants;
import ru.balancetracker.service.TransactionAccountService;

import java.util.List;

@RestController
@RequestMapping("/transaction-account")
@AllArgsConstructor
public class TransactionAccountController {
    private TransactionAccountService service;

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-purse")
    public Long createPurse(TransactionAccountDTO transactionAccountDTO){
        return service.createPurse(transactionAccountDTO);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-outcome")
    public Long createOutcome(TransactionAccountDTO transactionAccountDTO){
        return service.createOutcome(transactionAccountDTO);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-income")
    public Long createIncome(TransactionAccountDTO transactionAccountDTO){
        return service.createIncome(transactionAccountDTO);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/{userId}/purses")
    public List<TransactionAccount> getPursesForUser(@PathVariable String userId){
        return service.getPursesForUser(userId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/{userId}/outcome")
    public List<TransactionAccount> getOutcomesForUser(@PathVariable String userId){
        return service.getOutcomeForUser(userId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/{userId}/income")
    public List<TransactionAccount> getIncomesForUser(@PathVariable String userId){
        return service.getIncomeForUser(userId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable Long id){
        service.deleteTransactionAccount(id);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PutMapping("/{transactionAccountId}/update")
    public void update(@PathVariable Long transactionAccountId,
                       @RequestBody TransactionAccountDTO transactionDTO){
        service.update(transactionAccountId, transactionDTO);
    }

}
