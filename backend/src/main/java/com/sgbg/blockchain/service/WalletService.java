package com.sgbg.blockchain.service;

import com.sgbg.blockchain.common.exception.NoWalletException;
import com.sgbg.blockchain.common.exception.WrongPasswordException;
import com.sgbg.blockchain.domain.Transaction;
import com.sgbg.blockchain.domain.WalletHistory;
import com.sgbg.blockchain.repository.TransactionRepository;
import com.sgbg.blockchain.repository.WalletHistoryRepository;
import com.sgbg.blockchain.repository.WalletRepository;
import com.sgbg.blockchain.service.interfaces.IWalletService;
import com.sgbg.blockchain.domain.Wallet;
import com.sgbg.blockchain.wrapper.Contracts_Cash_sol_Cash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class WalletService implements IWalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    Web3j web3j;

    @Value("${eth.cash.contract}")
    String cashContractAddress;

    @Value("${eth.admin.address}")
    String admin;

    @Value("${eth.admin.private}")
    String adminPrivateKey;

    @Autowired
    WalletHistoryRepository walletHistoryRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Wallet createWallet(long userId, String password) throws Exception {
        // 프라이빗 네트워크에 사용할 지갑을 등록
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        String privateKey = ecKeyPair.getPrivateKey().toString(16);
        String publicKey = ecKeyPair.getPublicKey().toString(16);


        WalletFile walletFile = org.web3j.crypto.Wallet.createLight(password, ecKeyPair);
        String address = walletFile.getAddress();

        Wallet wallet = Wallet.builder()
                .ownerId(userId)
                .password(password)
                .publicKey(publicKey)
                .privateKey(privateKey)
                .address(address)
                .build();
        walletRepository.save(wallet);

        // --------------- 개발 하면 지울 부분 -------------------
        // Credentials 테스트
        Credentials credentials = Credentials.create(ecKeyPair);
        Credentials credentials1 = Credentials.create(privateKey, publicKey);
        Credentials credentials2 = Credentials.create(privateKey);
        if (credentials1.getAddress().equals(credentials.getAddress())) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println("========== 지갑 생성=========");
        System.out.println(privateKey);
        System.out.println(address); // 지갑 address
        System.out.println(credentials.getAddress());
        System.out.println(credentials1.getAddress());
        System.out.println(credentials2.getAddress());
        System.out.println("===================");
        // --------------- 개발 하면 지울 부분 end -------------------

        return wallet;
    }

    @Override
    public Wallet charge(long userId, long money) throws Exception {

        Wallet wallet = walletRepository.findByOwnerId(userId).orElse(null);
        if (wallet == null) {
            throw new NoWalletException();
        }

        long moneyBeforeTransaction = wallet.getCash();
        String privateKey = wallet.getPrivateKey();
        String publicKey = wallet.getPublicKey();
        Credentials credentials = Credentials.create(privateKey, publicKey);
        String address = credentials.getAddress();
        System.out.println(address);


        // -------------- 스마트 컨트랙트 함수 ---------------
        // 지갑 address와 money를 통해 우리가 만든 토큰을 충전한다.
        Credentials credentialsAdmin = Credentials.create(adminPrivateKey);
        ContractGasProvider contractGasProvider = new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);
        Contracts_Cash_sol_Cash cashContract = Contracts_Cash_sol_Cash.load(cashContractAddress, web3j, credentialsAdmin, contractGasProvider);

        cashContract.approve(admin, BigInteger.valueOf(money)).send();
        BigInteger num = cashContract.balanceOf(address).send();
        System.out.println(num);
        TransactionReceipt receipt = cashContract.transferFrom(admin, address, BigInteger.valueOf(money)).send();

        // TransactionReceipt를 가지고 Transaction 엔티티만들어서 저장한다.
        Transaction transaction = Transaction.builder()
                .hash(receipt.getTransactionHash()).contractAddress(receipt.getContractAddress())
                .blockHash(receipt.getBlockHash()).blockNumber(receipt.getBlockNumber().longValue())
                .transactionIndex(receipt.getTransactionIndex().longValue())
                .from(receipt.getFrom()).to(receipt.getTo())
                .money(money).gas(receipt.getGasUsed().longValue())
                .storedAt(LocalDateTime.now()).relatedToMoney(true).build();
        transactionRepository.save(transaction);
        // -----------------------------------------------

        // 스마트 컨트랙트를 사용하여 돈을 충전했다면 db에도 반영시킨다.
        wallet.setCash(moneyBeforeTransaction + money);

        // 또한 walletHistory에 type=charge로 생성하여 추가한다.
        WalletHistory walletHistory = WalletHistory.builder()
                .wallet(wallet)
                .totalMoneyBeforeTransaction(moneyBeforeTransaction)
                .money(money)
                .createdAt(LocalDateTime.now())
                .type("charge")
                .build();
        walletHistoryRepository.save(walletHistory);

        return wallet;
    }

    @Override
    public void checkWallet(long userId) throws Exception {
        // 지갑이 있는지 체크한다.
        // 지갑이 없는 경우엔 NoWalletException을 발생시킨다.
        // get과 나눈 이유는 프론트에서 처음 화면에서 비밀번호를 띄울지 지갑생성을 띄울지 미리 알아야 하기 때문이다.
        // get에서는 password를 받고 있기 때문에 둘을 나눴다.
        Wallet wallet = walletRepository.findByOwnerId(userId).orElse(null);
        if (wallet == null) {
            throw new NoWalletException();
        }
    }

    @Override
    public Wallet getWallet(long userId, String password) throws Exception {

        Wallet wallet = walletRepository.findByOwnerIdAndPassword(userId, password).orElse(null);
        if (wallet == null) {
            throw new WrongPasswordException();
        }
        return wallet;
    }

    @Override
    public List<WalletHistory> getHistoryList(long userId) throws Exception {
        // userId를 통해 walletId를 구한다.
        // walletId를 통해 List<WalletHistory>를 구한다.

        Wallet wallet = walletRepository.findByOwnerId(userId).orElse(null);
        if (wallet == null) {
            throw new NoWalletException();
        }

        return walletHistoryRepository.findAllByWalletOrderByCreatedAtDesc(wallet);
    }

    // 오직 Cash.sol 한번 배포할때만 잠시 사용하기
    @Override
    public String deployAdminCash() throws Exception {

        Wallet wallet = walletRepository.findByOwnerId(4L).orElse(null);
        if(wallet == null){
            return null;
        }
        String privateKey = wallet.getPrivateKey();
//        String publicKey = wallet.getPublicKey();
        Credentials credentials = Credentials.create(privateKey);
        System.out.println("credential address : " + credentials.getAddress());
        System.out.println("address : " + wallet.getAddress());
        ContractGasProvider contractGasProvider = new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);
        Contracts_Cash_sol_Cash cash_sol_cash = Contracts_Cash_sol_Cash.deploy(web3j, credentials, contractGasProvider, BigInteger.valueOf(10000000000L)).send();
        return cash_sol_cash.getContractAddress();
    }

//    public

//    @Override
//    public void makeAdmin() throws Exception{
//        long userId = 1;
//        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//        String privateKey = ecKeyPair.getPrivateKey().toString(16);
//        String publicKey = ecKeyPair.getPublicKey().toString(16);
//
//
//        WalletFile walletFile = org.web3j.crypto.Wallet.createLight("ssafy" , ecKeyPair);
//        String address = walletFile.getAddress();
//
//        Wallet wallet = Wallet.builder()
//                .ownerId(userId)
//                .password("ssafy")
//                .publicKey(publicKey)
//                .privateKey(privateKey)
//                .address(address)
//                .build();
//        walletRepository.save(wallet);
//
//    }

}
