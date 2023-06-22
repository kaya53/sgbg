package com.sgbg.blockchain.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WalletHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_history_id")
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private long totalMoneyBeforeTransaction; // 이번 트랜잭션이 일어나기 전의 지갑의 총 금액

    private long money; // 이번 트랜잭션에서 사용된 금액

    private LocalDateTime createdAt;

    private String type; // 충전(charge), 방입장(enter), 방퇴장(exit)

    private long roomId;

    private String roomName;

    @Builder
    public WalletHistory(Wallet wallet, long totalMoneyBeforeTransaction, long money, LocalDateTime createdAt,
                         String type, long roomId, String roomName){

        this.wallet = wallet;
        this.totalMoneyBeforeTransaction = totalMoneyBeforeTransaction;
        this.money = money;
        this.createdAt = createdAt;
        this.type = type;
        this.roomId = roomId;
        this.roomName = roomName;
    }
}
