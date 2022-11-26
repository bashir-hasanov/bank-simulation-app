package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {

    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();

    public TransactionDTO save(TransactionDTO transactionDTO) {
        transactionDTOList.add(transactionDTO);
        return transactionDTO;
    }

    public List<TransactionDTO> findAll() {
        return transactionDTOList;
    }

    public List<TransactionDTO> lastTransaction() {
        // write a stream that sort transactions based on creation date
        // and only return 10 of them
        return transactionDTOList.stream()
                .sorted(Comparator.comparing(TransactionDTO::getCreationDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> findTransactionsById(Long id) {

        return transactionDTOList.stream()
                .filter(transactionDTO -> transactionDTO.getSender().equals(id) ||
                        transactionDTO.getReceiver().equals(id))
                .collect(Collectors.toList());
    }
}