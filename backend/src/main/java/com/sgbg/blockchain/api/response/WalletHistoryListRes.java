package com.sgbg.blockchain.api.response;

import com.sgbg.blockchain.domain.WalletHistory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WalletHistoryListRes extends BaseResponseBody {

    private List<WalletHistory> walletHistoryList = new ArrayList<>();

    public static WalletHistoryListRes of(Integer statusCode, String message, List<WalletHistory> walletHistoryList){
        WalletHistoryListRes walletHistoryListRes = new WalletHistoryListRes();
        walletHistoryListRes.setStatusCode(statusCode);
        walletHistoryListRes.setMessage(message);
        walletHistoryListRes.setWalletHistoryList(walletHistoryList);
        return walletHistoryListRes;
    }

}
