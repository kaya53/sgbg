package com.sgbg.blockchain.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long id;

    private Long roomId;

    private String hash;

    private String contractAddress;

    private String blockHash;

    private long blockNumber;

    private long transactionIndex;

    @Column(name = "transaction_from")
    private String from;

    @Column(name = "transaction_to")
    private String to;

    private long money;

    private long gas;

    private LocalDateTime storedAt;

    private Boolean relatedToMoney;

    @Builder
    public Transaction(long id, Long roomId, String hash, String contractAddress, String blockHash, long blockNumber, long transactionIndex, String from, String to, long money, long gas, LocalDateTime storedAt, Boolean relatedToMoney) {
        this.id = id;
        this.roomId = roomId;
        this.hash = hash;
        this.contractAddress = contractAddress;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.transactionIndex = transactionIndex;
        this.from = from;
        this.to = to;
        this.money = money;
        this.gas = gas;
        this.storedAt = storedAt;
        this.relatedToMoney = relatedToMoney;
    }
}
