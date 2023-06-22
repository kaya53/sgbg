package com.sgbg.blockchain.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WalletChargeReq {

    @Schema(name = "money")
    private long money;

}
