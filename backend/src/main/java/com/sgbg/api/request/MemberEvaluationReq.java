package com.sgbg.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberEvaluationReq {

    @Schema(name = "evaluations")
    List<BaseMemberEvaluationReq> evaluations = new ArrayList<>();

}
