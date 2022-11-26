package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public interface AccountRepository extends JpaRepository<Account, Long> {

//    public static List<AccountDTO> accountDTOList = new ArrayList<>();
//
//    public AccountDTO save(AccountDTO accountDTO) {
//        accountDTOList.add(accountDTO);
//        return accountDTO;
//    }
//
//    public List<AccountDTO> findAll() {
//        return accountDTOList;
//    }
//
//    public AccountDTO findById(Long id) {
//        /*
//        write a method that finds the account inside the list,
//        if not,
//        throws RecordNotFoundException
//         */
//      return accountDTOList.stream()
//              .filter(account -> account.getId().equals(id))
//              .findAny().orElseThrow(() ->
//                      new RecordNotFoundException("Account not exists in Database."));
//    }
}
