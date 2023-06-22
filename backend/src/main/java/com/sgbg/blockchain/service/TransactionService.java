package com.sgbg.blockchain.service;

import com.sgbg.blockchain.domain.Transaction;
import com.sgbg.blockchain.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByRoom(Long roomId) {
        return transactionRepository.findTransactionsByRoomId(roomId);
    }



}
