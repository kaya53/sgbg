package com.sgbg.blockchain.domain;

import com.sgbg.domain.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wallet_id")
	private long id;

	private long ownerId; // userId
	private String password; // 유저의 비밀번호
	private String publicKey;
	private String privateKey;
	private String address;

	private long cash = 0;

	// 우리는 이더를 사용하지 않을 것인데 이것들을 저장해야 하나??
//	private BigDecimal balance = BigDecimal.valueOf(0);

	@Builder
	public Wallet(long ownerId, String password, String publicKey, String privateKey, String address) {
		this.ownerId = ownerId;
		this.password = password;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.address = address;
	}

}
