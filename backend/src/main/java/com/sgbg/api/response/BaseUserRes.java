package com.sgbg.api.response;

import com.sgbg.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseUserRes {

    @Schema(description = "회원 번호", example = "1234567")
    private String kakaoId;

    @Schema(description = "이름", example = "Bungle")
    private String name;

    @Schema(description = "이메일", example = "sgbg@sgbg.com")
    private String email;

    @Schema(description = "방장 신뢰도 점수", example = "95")
    private int hostScore;

    @Schema(description = "참가자 점수", example = "84")
    private int memberScore;

    public static BaseUserRes of(User user, String kakaoId) {
        BaseUserRes res = new BaseUserRes();
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setHostScore(user.getHostScore());
        res.setMemberScore(user.getMemberScore());
        res.setKakaoId(kakaoId);
        return res;
    }
}
