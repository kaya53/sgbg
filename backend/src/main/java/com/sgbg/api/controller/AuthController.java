package com.sgbg.api.controller;

import com.sgbg.api.response.BaseResponseBody;
import com.sgbg.api.response.UserRes;
import com.sgbg.common.util.CookieUtil;
import com.sgbg.domain.Auth;
import com.sgbg.domain.User;
import com.sgbg.service.AuthService;
import com.sgbg.service.KakaoService;
import com.sgbg.service.RedisService;
import com.sgbg.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Tag(name = "Auth API", description = "사용자 인증을 위한 로그인, 로그아웃 기능 제공")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CookieUtil cookieUtil;

    @Operation(summary = "카카오 로그인 메서드")
    @ApiResponses({
            @ApiResponse(responseCode = "2000", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = UserRes.class))),
            @ApiResponse(responseCode = "2010", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = UserRes.class)))
    })
    @GetMapping("/login")
    public ResponseEntity<? extends BaseResponseBody> kakaoLogin(
            @RequestParam String code, HttpServletResponse response) throws IOException {

        User user = null;
        Auth isUser = null;
        String kakaoId = null;

        // 1. 인가 code로 Kakao Auth Server에서 token 받기
        try {
            Map<String, String> tokenInfo = kakaoService.getKakaoTokenInfo(code);

            // 2. access token으로 Kakao Resource Server에서 user 정보 갖고 오기
            Map<String, String> userInfo = kakaoService.getKakaoUserInfo(tokenInfo.get("access_token"));

            // 3. 회원 가입이 안되어 있는 경우, 회원가입 시키기
            kakaoId = userInfo.get("id");
            isUser = authService.isUser(kakaoId);

            if (isUser != null) {
                user = isUser.getUser();
            } else {
                user = userService.createUser(userInfo);
                authService.createAuth(user, kakaoId);
            }

            // 4. Redis에 token 과 user 정보 저장
            redisService.saveToken(
                    user.getId(), "access_token", tokenInfo.get("access_token"), Long.valueOf(tokenInfo.get("expires_in")));
            redisService.saveToken(
                    user.getId(), "refresh_token", tokenInfo.get("refresh_token"), Long.valueOf(tokenInfo.get("refresh_token_expires_in"))
            );

            // 5. Cookie에 token 저장
            String accessToken = tokenInfo.get("access_token");
            String refreshToken = tokenInfo.get("refresh_token");

            Cookie accessCookie = cookieUtil.addAccessToken(accessToken);
            Cookie refreshCookie = cookieUtil.addRefreshToken(refreshToken);

            response.addCookie(accessCookie);
            response.addCookie(refreshCookie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isUser == null) {
            return ResponseEntity.status(HttpStatus.OK).body(UserRes.of(2010, "Success", kakaoId, user));
        }
        return ResponseEntity.status(HttpStatus.OK).body(UserRes.of(2000, "Success", kakaoId, user));
    }

    @Operation(summary = "카카오 로그아웃 메서드")
    @ApiResponses(
            @ApiResponse(responseCode = "2000", description = "로그아웃 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponseBody.class)))
    )
    @GetMapping("/logout")
    public ResponseEntity<? extends BaseResponseBody> logout(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> tokenInfo = cookieUtil.getTokenInfo(request);
        String accessToken = tokenInfo.get("access_token");
        String refreshToken = tokenInfo.get("refresh_token");

        kakaoService.logout(accessToken);

        Cookie accessCookie = new Cookie("accessToken", null);
        accessCookie.setMaxAge(0);
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        // Redis Token 삭제
        redisService.deleteToken("access_token", accessToken);
        redisService.deleteToken("refresh_token", refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(2000, "Success"));
    }

    @GetMapping("/refresh")
    public void updateToken() {
        // TODO: update access/refresh token 구현

    }

}
