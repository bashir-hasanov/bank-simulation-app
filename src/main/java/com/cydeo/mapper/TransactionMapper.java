package com.cydeo.mapper;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper;


    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Transaction convertToEntity(TransactionDTO dto) {
        return modelMapper.map(dto, Transaction.class);
    }

    public TransactionDTO convertToDTO(Transaction entity) {
        return modelMapper.map(entity, TransactionDTO.class);
    }
}
