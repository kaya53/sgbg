package com.sgbg.repository;

import com.sgbg.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByKakaoNumber(Long kakaoNumber);
}