package com.sgbg.blockchain.api.controller;

import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {

    // 매번 스마트 컨트랙트를 호출하는 경우에는 트랜잭션을 만들어서 db에 넣고,
    // 프론트에서 특정 해시값을 가지고 조회를 한다면 트랜잭션에 대한 정보를 넘겨준다.
}
