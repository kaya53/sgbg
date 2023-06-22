package com.sgbg.service.interfaces;


import com.sgbg.domain.Auth;
import com.sgbg.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAuthService {

    public Auth isUser(String kakaoId);
    public void createAuth(User user, String kakaoId);

}
