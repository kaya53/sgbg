package com.sgbg.blockchain.repository;

import com.sgbg.blockchain.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByOwnerId(Long userId);
    Optional<Wallet> findByOwnerIdAndPassword(long userId, String password);


}
