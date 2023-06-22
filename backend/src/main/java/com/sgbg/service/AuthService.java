package com.sgbg.service;


import com.sgbg.domain.Auth;
import com.sgbg.domain.User;
import com.sgbg.repository.AuthRepository;
import com.sgbg.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthService implements IAuthService {

    @Autowired
    AuthRepository authRepository;

    public Auth isUser(String kakaoId) {
        Long kakaoNumber = Long.valueOf(kakaoId);
        return authRepository.findByKakaoNumber(kakaoNumber).orElse(null);
    }

    public void createAuth(User user, String kakaoId) {
        Auth auth = Auth.builder()
                .kakaoNumber(Long.valueOf(kakaoId))
                .user(user)
                .build();

        authRepository.save(auth);
    }
}
