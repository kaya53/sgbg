package com.sgbg.blockchain.api.response;

import com.sgbg.blockchain.domain.Wallet;
import com.sgbg.blockchain.domain.WalletHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@Getter
@ToString
public class WalletHistoryRes extends BaseResponseBody{

    @Schema(name = "walletHistoryId")
    private long id;
    @Schema(name = "wallet", example = "이 히스토리의 대상 지갑")
    private Wallet wallet;
    @Schema(name = "totalMoneyBeforeTransaction", example = "이번 거래가 발생하기 직전의 지갑의 총 금액")
    private long totalMoneyBeforeTransaction;
    @Schema(name = "money", example = "이번 거래에 사용되는 금액")
    private long money;
    @Schema(name = "createdAt", example = "이 거래가 발생한 시간")
    private LocalDateTime createdAt;
    @Schema(name = "type", example = "충전(charge), 방입장(enter), 방퇴장(exit)")
    private String type;
    @Schema(name = "roomId", example = "방 Id")
    private long roomId;
    @Schema(name = "roomName", example = "방 이름")
    private String roomName;

    public static WalletHistoryRes getWalletHistoryRes (WalletHistory walletHistory){
        WalletHistoryRes walletHistoryRes = new WalletHistoryRes();
        walletHistoryRes.setId(walletHistory.getId());
        walletHistoryRes.setWallet(walletHistory.getWallet());
        walletHistoryRes.setTotalMoneyBeforeTransaction(walletHistory.getTotalMoneyBeforeTransaction());
        walletHistoryRes.setMoney(walletHistory.getMoney());
        walletHistoryRes.setCreatedAt(walletHistory.getCreatedAt());
        walletHistoryRes.setType(walletHistory.getType());
        walletHistoryRes.setRoomId(walletHistory.getRoomId());
        walletHistoryRes.setRoomName(walletHistory.getRoomName());

        return walletHistoryRes;
    }

    public static WalletHistoryRes of(Integer statusCode, String message, WalletHistory walletHistory){
        WalletHistoryRes walletHistoryRes = getWalletHistoryRes(walletHistory);
        walletHistoryRes.setStatusCode(statusCode);
        walletHistoryRes.setMessage(message);

        return walletHistoryRes;
    }
}
