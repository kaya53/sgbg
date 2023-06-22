//package com.sgbg.blockchain.domain.factory;
//
//import com.sgbg.blockchain.domain.Wallet;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class WalletFactory
//{
//	public static Wallet create(ResultSet rs) throws SQLException
//	{
//		if (rs == null) return null;
//		Wallet wallet = new Wallet();
//		wallet.setId(rs.getLong("id"));
//		wallet.setOwnerId(rs.getLong("owner_id"));
//		wallet.setAddress(rs.getString("address"));
////		wallet.setBalance(rs.getBigDecimal("balance"));
////		wallet.setCash(rs.getInt("cash"));
//
//		return wallet;
//	}
//}
