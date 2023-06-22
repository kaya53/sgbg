package com.sgbg.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TransactionRes {

    @Schema(name = "hash", description = "트랜잭션 해시 문자열")
    private String hash;

    @Schema(name = "contractAddress", description = "배포 주소")
    private String contractAddress;

    @Column(name = "from")
    private String from;

    @Column(name = "fromName")
    private String fromName;

    @Column(name = "to")
    private String to;

    @Column(name = "toName")
    private String toName;

    @Column(name = "storedAt")
    private LocalDateTime storedAt;

    @Column(name = "money")
    private long money;


    public static TransactionRes of(String hash, String contractAddress, String from, String fromName, String to, String toName, LocalDateTime storedAt, long money) {
        TransactionRes res = new TransactionRes();
        res.setHash(hash);
        res.setContractAddress(contractAddress);
        res.setFrom(from);
        res.setFromName(fromName);
        res.setTo(to);
        res.setToName(toName);
        res.setStoredAt(storedAt);
        res.setMoney(money);
        return res;
    }
}
