package com.cydeo.service;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<AccountDTO> listAllAccount();

    void deleteAccount(Long id);

    AccountDTO retrieveById(Long id);
}
