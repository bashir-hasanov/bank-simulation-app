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
    public AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        AccountDTO accountDTO = new AccountDTO();
        return accountRepository.save(accountDTO);

    }

    @Override
    public List<AccountDTO> listAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream().map(accountMapper :: convertToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        AccountDTO accountDTO = accountRepository.findById(id);
        accountDTO.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return accountRepository.findById(id);
    }
}


