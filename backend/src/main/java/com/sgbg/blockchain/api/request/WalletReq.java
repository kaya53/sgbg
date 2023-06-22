package com.sgbg.blockchain.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WalletReq {

    @Schema(name = "password", description = "비밀번호")
    private String password;

}
