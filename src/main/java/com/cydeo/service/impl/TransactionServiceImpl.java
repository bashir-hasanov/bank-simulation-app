package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Transaction;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;

    AccountService accountService;
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;




    @Override
    public void makeTransfer(AccountDTO sender, AccountDTO receiver,
                                       BigDecimal amount, Date creationDate, String message) {

        if (!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
        /*
        after all validations completed, and money is transferred
        we need to create Transaction object and save/return it
        Please create needed classes/methods for this step, save the transactions
         */
            // we need to create DTO after all validations, then convert it to entity and save to DB
            TransactionDTO transactionDTO = new TransactionDTO();

             transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));

        } else {
            throw new UnderConstructionException("App is under construction, try again later.");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if (checkSenderBalance(sender, amount)) {
            //make transaction
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            // Get the DTO from DB for both sender and receiver, update balance and save it
            // create accountService updateAccount method to save it
          AccountDTO senderAcc = accountService.retrieveById(sender.getId());
          senderAcc.setBalance(sender.getBalance());
          // save again to DB
            accountService.updateAccount(senderAcc);

            AccountDTO receiverAcc = accountService.retrieveById(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAcc);

        } else {
            //not enough balance
            throw new BalanceNotSufficientException("Balance is not sufficient for this transfer!");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify that sender has enough balance
       return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        /*
        write an if statement that checks
        if one of the accounts is saving,
        and user id of sender or receiver is not the same,
        throw AccountOwnershipException
         */
        if ((sender.getAccountType().equals(AccountType.SAVING)
                || receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnershipException("One of the accounts is SAVINGS." +
                    " Transactions between SAVINGS and CHECKING accounts are allowed between same user accounts only." +
                    " User IDs do not match.");
        }
    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        /*
        -if any of the accounts is null
        -if account IDs are the same (same account)
        -if the account exists in the DB (repository)
         */
        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender account needs to be different from Receiver");
        }

        findAccountById (sender.getId());
        findAccountById (receiver.getId());
    }

    private AccountDTO findAccountById(Long id) {
       return accountService.retrieveById(id);
    }

    @Override
    public List<TransactionDTO> findAllTransactions() {
        List<Transaction> transactionList = transactionRepository.findAll();
        return transactionList.stream().map(transactionMapper :: convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> lastTransactionsList() {
        // we want to list latest 10 transactions
        // write a native query to get the result for last 10 transactions
        // then convert it to dto and return it
        return transactionRepository.findLastTenTransactions()
                .stream().map(transactionMapper :: convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        // write a JPQL query to retrieve list of Transactions by ID
        return transactionRepository.findTransactionListById(id)
                .stream().map(transactionMapper :: convertToDTO).collect(Collectors.toList());
    }
}
