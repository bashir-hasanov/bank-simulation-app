package com.cydeo.controller;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {

    /*
       write a method to return index.html page including account list
     */
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("accountList", accountService.listAllAccount());

        return "account/index";
    }

    @GetMapping("/create-user")
    public String getCreateUser(Model model) {

        model.addAttribute("account", AccountDTO.builder().build());
        model.addAttribute("accountTypes", AccountType.values());
        return "account/create-account";
    }

    // create a /create post method that creates account in the service
    // then return the index page
    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") AccountDTO accountDTO, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }

        accountService.createNewAccount(accountDTO.getBalance(), new Date(),
                accountDTO.getAccountType(), accountDTO.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String getDeletedId(@PathVariable("id") Long id) {

        //find the account and change status to deleted
        accountService.deleteAccount(id);
        System.out.println(id);
        return "redirect:/index";
    }
}
