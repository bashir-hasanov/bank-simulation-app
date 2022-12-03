package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query(value = "SELECT * FROM transactions ORDER BY creationDate DESC LIMIT 10", nativeQuery = true)
    List<Transaction> findLastTenTransactions();

    @Query("SELECT t FROM Transaction t WHERE t.sender.id = ?1 OR t.receiver.id = ?1")
    List<Transaction> findTransactionListById(@Param("id") Long id);

}