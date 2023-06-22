package com.sgbg.api.response;

import com.sgbg.blockchain.domain.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class TransactionListRes {

    @Schema(name = "transactions", description = "트랜잭션 목록")
    private List<TransactionRes> transactions = new ArrayList<>();

    public static TransactionListRes of(List<Transaction> transactionList, List<String> fromNames, List<String> toNames) {
        TransactionListRes res = new TransactionListRes();
        res.setTransactions(transactionList, fromNames, toNames);
        return res;
    }

    public void setTransactions(List<Transaction> transactionList, List<String> fromNames, List<String> toNames) {
        for (int i = 0; i < transactionList.size(); i++) {
            Transaction transaction = transactionList.get(i);
            this.transactions.add(TransactionRes.of(
                    transaction.getHash(), transaction.getContractAddress(), transaction.getFrom(), fromNames.get(i),
                    transaction.getTo(), toNames.get(i), transaction.getStoredAt(), transaction.getMoney()
            ));
        }
    }

}
