package com.sgbg.blockchain.service.interfaces;

import com.sgbg.blockchain.common.exception.NotEnoughMoneyException;
import com.sgbg.blockchain.domain.Transaction;
import com.sgbg.blockchain.domain.Wallet;

public interface ISingleBungleService {

    String createRoom(Long roomId, long hostId, long duration, long minimumAmount) throws Exception, NotEnoughMoneyException;

    Wallet enterRoom(Long roomId, long userId, long hostId, String sgbgContractAddress, long money) throws Exception;

    Wallet exitRoom(Long roomId, long userId, long hostId, String sgbgContractAddress, long money) throws Exception;

    Wallet endRoom(long roomId, long hostId, String sgbgContractAddress) throws Exception;

    Transaction isSuccess(long roomId, long userId, boolean isSuccess, long hostId, String sgbgContractAddress) throws Exception;
}
