package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.UUID;

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
        return "/transaction/make-transfer";

    }

    //write a post method that takes transaction object from the method above,
    //complete the make transfer and return the same page
    @PostMapping("/transfer")
    public String saveTransfer(@ModelAttribute("transaction") Transaction transaction, Model model) {

        Account sender = accountService.retrieveById(transaction.getSender());
        Account receiver = accountService.retrieveById(transaction.getReceiver());
        transactionService.makeTransfer(sender, receiver, transaction.getAmount(),
                new Date(), transaction.getMessage());

        return "redirect:/make-transfer";

    }

    @GetMapping("/transaction/{id}")
    public String getTransactionList(@PathVariable("id") UUID id, Model model) {

        System.out.println(id);

        // get the list of transactions based on ID and return as a model of attribute
        model.addAttribute("transactions", transactionService.findTransactionListById(id));

        return "/transaction/transactions";
    }
}
