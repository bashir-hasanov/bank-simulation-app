package com.cydeo.controller;

import com.cydeo.model.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/make-transfer")
    public String makeTransfer(Model model) {

        // 1. we need all accounts tp provide them as SENDER, RECEIVER
        model.addAttribute("accounts", accountService.listAllAccount());

        // 2. we need empty transaction object to get info from UI
        model.addAttribute("transaction", Transaction.builder().build());

        // 3. we need list of last 10 transactions
        model.addAttribute("lastTransactions", transactionService.lastTransactionsList());
        return "transaction/make-transfer";

    }
}
