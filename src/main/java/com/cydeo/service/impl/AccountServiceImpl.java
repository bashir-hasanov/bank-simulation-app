package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {

        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountDTO.setCreationDate(new Date());
        accountRepository.save(accountMapper.convertToEntity(accountDTO));

    }

    @Override
    public List<AccountDTO> listAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream().map(accountMapper :: convertToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);

    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return accountMapper.convertToDto(accountRepository.findById(id).get());
    }

    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        // 1. We need active accounts from AccountRepository
        List<Account> accountList = accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE);
        // 2. Convert active accounts to dto and return
        return accountList.stream().map(accountMapper :: convertToDto).collect(Collectors.toList());
    }

}


