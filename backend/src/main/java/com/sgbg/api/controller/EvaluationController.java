package com.sgbg.api.controller;

import com.sgbg.api.request.BaseMemberEvaluationReq;
import com.sgbg.api.request.HostEvalutionReq;
import com.sgbg.api.request.MemberEvaluationReq;
import com.sgbg.api.response.BaseResponseBody;
import com.sgbg.blockchain.domain.Transaction;
import com.sgbg.blockchain.domain.Wallet;
import com.sgbg.blockchain.service.interfaces.ISingleBungleService;
import com.sgbg.common.util.exception.NotFoundException;
import com.sgbg.common.util.CookieUtil;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import com.sgbg.service.*;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Evaluation API", description = "방장 평가, 참여자 평가 기능 제공")
@RestController
@RequestMapping("/eval")
public class EvaluationController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    HostEvaluationService hostEvaluationService;

    @Autowired
    MemberEvaluationService memberEvaluationService;

    @Autowired
    ISingleBungleService singleBungleService;

    @Autowired
    CookieUtil cookieUtil;

    @Operation(summary = "방장 평가 메서드")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "방장 평가 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponseBody.class))),
            @ApiResponse(responseCode = "500", description = "방장 평가 실패")
    })
    @PostMapping("/host/{roomId}")
    public ResponseEntity<? extends BaseResponseBody> hostEvaluation(@PathVariable String roomId,
                                                                     @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "모임 성공/실패 여부 확인", required = true,
                                                                             content = @Content(schema = @Schema(implementation = HostEvalutionReq.class))) HostEvalutionReq hostEvaluation,
                                                                     HttpServletRequest request) {
        Room room = roomService.getRoom(Long.valueOf(roomId));
        if (room == null) {
            throw new NotFoundException("Room Not Found");
        }

        Long userId = cookieUtil.getUserIdByToken(request);
        User user = userService.getUserById(userId);

        Boolean isSuccess = hostEvaluation.getIsSuccess();

        try {
            Transaction transaction = singleBungleService.isSuccess(room.getId(), userId, isSuccess, room.getHostId(), room.getContractAddress());
            hostEvaluationService.createEvaluation(user, room, isSuccess, transaction.getId());

            if(!isSuccess) { // 실패 시, host의 hostScore 변경
                userService.updateHostScore(room.getHostId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(2010, "Success"));
    }

    @Operation(summary = "참여자 평가 메서드")
    @PostMapping("/member/{roomId}")
    public ResponseEntity<? extends BaseResponseBody> memberEvaluation(@PathVariable String roomId,
                                                                       @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "모임 참여자에 대한 평가", required = true,
                                                                               content = @Content(schema = @Schema(implementation = MemberEvaluationReq.class)))
                                                                       MemberEvaluationReq memberEvaluationInfo,
                                                                       HttpServletRequest request) {
        Room room = roomService.getRoom(Long.valueOf(roomId));
        if (room == null) {
            throw new NotFoundException("Room Not Found");
        }

        Long userId = cookieUtil.getUserIdByToken(request);
        User evaluator = userService.getUserById(userId);

        List<BaseMemberEvaluationReq> memberEvaluations = memberEvaluationInfo.getEvaluations();
        if (memberEvaluations == null) {
            throw new NotFoundException("Evaluation Request Not Received");
        }

        for (BaseMemberEvaluationReq memberEvaluation : memberEvaluations) {
            Long kakaoId = memberEvaluation.getKakaoId();
            User user = authService.isUser(String.valueOf(kakaoId)).getUser();

            memberEvaluationService.createEvaluation(memberEvaluation, room, evaluator, user);
            userService.updateMemberScore(user.getId(), memberEvaluation.getReview().getScore());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseBody.of(2010, "Success"));
    }

}
