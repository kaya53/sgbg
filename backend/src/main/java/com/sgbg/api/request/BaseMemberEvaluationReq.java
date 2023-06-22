package com.sgbg.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseMemberEvaluationReq {

    @Schema(name = "kakaoId", description = "평가 대상의 카카오 회원 번호")
    private Long kakaoId;

    @Schema(name = "review", description = "평가", example = "BEST")
    private Review review;

}
