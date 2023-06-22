package com.sgbg.common.util;

import com.sgbg.common.util.exception.NotFoundException;
import com.sgbg.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class CookieUtil {

    @Autowired
    private RedisService redisService;

    public Cookie addAccessToken(String accessToken) {
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setMaxAge((int) System.currentTimeMillis() * 1800 * 1000);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie addRefreshToken(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setMaxAge(86400 * 1000);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public Map<String, String> getTokenInfo(HttpServletRequest request) {
        Map<String, String> tokens = new HashMap<>();

        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if ("accessToken".equals(c.getName())) {
                tokens.put("access_token", c.getValue());
            } else if ("refreshToken".equals(c.getName())) {
                tokens.put("refresh_token", c.getValue());
            }
        }
        return tokens;
    }

    public Long getUserIdByToken(HttpServletRequest request) {
        Map<String, String> tokenInfo = getTokenInfo(request);
        String accessToken = tokenInfo.get("access_token");
        String userId = redisService.getUserIdByToken("access_token", accessToken);

        if(userId == null) {
            throw new NotFoundException("로그인 된 사용자가 아닙니다.");
        }
        return Long.parseLong(userId);
    }
}
