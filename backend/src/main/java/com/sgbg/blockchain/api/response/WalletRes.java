package com.sgbg.blockchain.api.response;

import com.sgbg.blockchain.domain.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@NoArgsConstructor
@Getter
@ToString
public class WalletRes extends BaseResponseBody {

    @Schema(name = "walletId")
    private long id;
    @Schema(name = "ownerId", example = "지갑의 소유자 id")
    private long ownerId;
    @Schema(name = "address", example = "지갑의 주소")
    private String address;
    @Schema(name = "cash", example = "지갑이 가지고 있는 현재의 금액")
    private long cash;

    public static WalletRes getWalletRes(Wallet wallet){
        WalletRes walletRes = new WalletRes();
        walletRes.setId(wallet.getId());
        walletRes.setOwnerId(wallet.getOwnerId());
        walletRes.setAddress(wallet.getAddress());
        walletRes.setCash(wallet.getCash());

        return walletRes;
    }

    public static WalletRes of(Integer statusCode, String message, Wallet wallet){
        WalletRes walletRes = getWalletRes(wallet);
        walletRes.setStatusCode(statusCode);
        walletRes.setMessage(message);

        return walletRes;
    }

}
