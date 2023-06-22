//package com.sgbg.blockchain.common.config;
//
//import com.sgbg.blockchain.domain.Wallet;
//import com.sgbg.blockchain.service.WalletService;
//import com.sgbg.blockchain.wrapper.Cash_sol_Cash;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.web3j.crypto.Credentials;
//import org.web3j.crypto.ECKeyPair;
//import org.web3j.crypto.Keys;
//import org.web3j.crypto.WalletFile;
//import org.web3j.protocol.Web3j;
//
//import java.math.BigInteger;
//
//@Configuration
//@EnableTransactionManagement
//public class CashContractConfig {
//
//    @Autowired
//    private Web3j web3j;
//
//    @Bean
//    public Cash_sol_Cash cashContract(){
//
//        try {
//            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//            String privateKey = ecKeyPair.getPrivateKey().toString(16);
//            String publicKey = ecKeyPair.getPublicKey().toString(16);
//            WalletFile walletFile = org.web3j.crypto.Wallet.createLight("ssafy", ecKeyPair);
//            String address = walletFile.getAddress();
//            System.out.println(address);
//
//            Credentials credentials = Credentials.create(privateKey,publicKey);
//            Cash_sol_Cash cashContract = Cash_sol_Cash.deploy(web3j, credentials, new BigInteger("0"), new BigInteger("1000000"), new BigInteger("100000000")).send();
//            cashContract.mint(address, new BigInteger("1000"));
//            System.out.println(cashContract.balanceOf(address).send());
//            return cashContract;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
