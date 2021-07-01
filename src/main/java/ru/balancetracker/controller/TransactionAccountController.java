package ru.balancetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balancetracker.model.dto.TransactionAccountDTO;
import ru.balancetracker.model.jpa.TransactionAccount;
import ru.balancetracker.security.SecurityConstants;
import ru.balancetracker.security.utils.SecurityUtils;
import ru.balancetracker.service.TransactionAccountService;

import java.util.List;

@RestController
@RequestMapping("/transaction-account")
@AllArgsConstructor
public class TransactionAccountController {
    private final TransactionAccountService service;

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-purse")
    public Long createPurse(@RequestBody TransactionAccountDTO transactionAccountDTO) {
        return service.createPurse(transactionAccountDTO);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-outcome")
    public Long createOutcome(@RequestBody TransactionAccountDTO transactionAccountDTO) {
        return service.createOutcome(transactionAccountDTO);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PostMapping("/create-income")
    public Long createIncome(@RequestBody TransactionAccountDTO transactionAccountDTO) {
        return service.createIncome(transactionAccountDTO);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/purses/all")
    public List<TransactionAccount> getPursesForUser() {
        String userId = SecurityUtils.getCurrentUser().getId();
        return service.getPursesForUser(userId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/outcome/all")
    public List<TransactionAccount> getOutcomesForUser() {
        String userId = SecurityUtils.getCurrentUser().getId();
        return service.getOutcomeForUser(userId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @GetMapping("/income/all")
    public List<TransactionAccount> getIncomesForUser() {
        String userId = SecurityUtils.getCurrentUser().getId();
        return service.getIncomeForUser(userId);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTransactionAccount(id);
    }

    @SecurityConstants.PreAuthorizeUserRole
    @PutMapping("/update/{transactionAccountId}")
    public void update(@PathVariable Long transactionAccountId,
                       @RequestBody TransactionAccountDTO transactionDTO) {
        service.update(transactionAccountId, transactionDTO);
    }

}
