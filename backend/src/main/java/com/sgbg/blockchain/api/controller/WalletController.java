package com.sgbg.blockchain.api.controller;

import com.google.api.Http;
import com.sgbg.blockchain.api.request.WalletChargeReq;
import com.sgbg.blockchain.api.request.WalletReq;
import com.sgbg.blockchain.api.response.BaseResponseBody;
import com.sgbg.blockchain.api.response.WalletHistoryListRes;
import com.sgbg.blockchain.api.response.WalletHistoryRes;
import com.sgbg.blockchain.api.response.WalletRes;
import com.sgbg.blockchain.common.exception.NoWalletException;
import com.sgbg.blockchain.common.exception.WrongPasswordException;
import com.sgbg.blockchain.domain.Wallet;
import com.sgbg.blockchain.domain.WalletHistory;
import com.sgbg.blockchain.service.interfaces.ISingleBungleService;
import com.sgbg.blockchain.service.interfaces.IWalletService;
import com.sgbg.common.util.CookieUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Wallet API", description = "사용자의 지갑 기능 제공")
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private IWalletService walletService;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private ISingleBungleService singleBungleService;

    @Value("${eth.admin.private}")
    private String adminPrivate;

//    @Value("${eth.admin.address}")
//    private String adminAddress;

    @GetMapping
    public ResponseEntity<? extends BaseResponseBody> checkWallet(HttpServletRequest request) {

        long userId = cookieUtil.getUserIdByToken(request);
//        long userId = 3L;

        try {
            walletService.checkWallet(userId);
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(2000, "Success"));
        } catch(NoWalletException e){
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(4020, "No Wallet"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping
    public ResponseEntity<? extends WalletRes> getWallet(@RequestBody WalletReq walletReq, HttpServletRequest request) {
        long userId = cookieUtil.getUserIdByToken(request);

        try {
            Wallet wallet = walletService.getWallet(userId, walletReq.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(WalletRes.of(2000, "Success", wallet));
        } catch (WrongPasswordException e){
            return ResponseEntity.status(HttpStatus.OK).body(WalletRes.of(4010, "Wrong Password", null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<? extends WalletRes> createWallet(@RequestBody WalletReq walletReq, HttpServletRequest request) {
//         CookieUtil의 getUserIdByToken을 사용하여 userId를 받기
        long userId = cookieUtil.getUserIdByToken(request);

        Wallet wallet = null;
        try {
            wallet = walletService.createWallet(userId, walletReq.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(wallet == null){
            return ResponseEntity.status(HttpStatus.OK).body(WalletRes.of(4030, "Fail", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(WalletRes.of(2010, "Accepted", wallet));
    }

    @PostMapping("/charge")
    public ResponseEntity<? extends BaseResponseBody> charge(@RequestBody WalletChargeReq walletChargeReq, HttpServletRequest request){
        // CookieUtil의 getUserIdByToken을 사용하여 userId를 받기
        long userId = cookieUtil.getUserIdByToken(request);

        try {
            Wallet wallet = walletService.charge(userId, walletChargeReq.getMoney());
            return ResponseEntity.status(HttpStatus.OK).body(WalletRes.of(2010, "Accepted", wallet));
        } catch (NoWalletException e){
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(4020, "No Wallet"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/history")
    public ResponseEntity<? extends BaseResponseBody> history(HttpServletRequest request){
        // CookieUtil의 getUserIdByToken을 사용하여 userId를 받기
        long userId = cookieUtil.getUserIdByToken(request);

        try {
            List<WalletHistory> walletHistoryList = walletService.getHistoryList(userId);
            return ResponseEntity.status(HttpStatus.OK).body(WalletHistoryListRes.of(2000, "Accepted", walletHistoryList));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/adminadminadminrealkiki")
    public ResponseEntity<String> deployAdminCash() throws Exception {

        String contract = walletService.deployAdminCash();
        System.out.println("controller : "+ contract);
        return ResponseEntity.status(HttpStatus.OK).body(contract);
    }

//    @PostMapping("/admin")
//    public ResponseEntity<String> makeAdmin() throws Exception{
////        walletService.makeAdmin();
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

    // test용
//    @PostMapping("/createroom")
//    public ResponseEntity<? extends BaseResponseBody> rooms(HttpServletRequest request){
//
//        long userId = 4L;
//
//        try {
//            String contractAddress = singleBungleService.createRoom(userId, 7, 100);
//            System.out.println("controller : " + contractAddress);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

//    @PostMapping("/enterroom")
//    public ResponseEntity<? extends BaseResponseBody> enterroom(){
//        long userId = 2L;
//
//        try{
//            singleBungleService.enterRoom(userId, 4L, "0x2ab6e06b5b10da7f8b3d23063f05c9649179843f", 100);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }

}
























