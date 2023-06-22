package com.sgbg.api.controller;

import com.sgbg.api.request.Review;
import com.sgbg.api.response.BaseResponseBody;
import com.sgbg.api.response.ReviewRes;
import com.sgbg.api.response.RoomListRes;
import com.sgbg.api.response.UserRes;
import com.sgbg.blockchain.service.interfaces.ISingleBungleService;
import com.sgbg.common.util.exception.NotFoundException;
import com.sgbg.common.util.CookieUtil;
import com.sgbg.domain.*;
import com.sgbg.service.RoomService;
import com.sgbg.service.interfaces.IAuthService;
import com.sgbg.service.interfaces.IHostEvaluationService;
import com.sgbg.service.interfaces.IMemberEvaluationService;
import com.sgbg.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "User API", description = "회원 등록, 회원 정보 조회 등의 기능 제공")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;
    private final IAuthService authService;

    private final RoomService roomService;

    private final IHostEvaluationService hostEvaluationService;

    private final IMemberEvaluationService memberEvaluationService;

    private final ISingleBungleService singleBungleService;
    private final CookieUtil cookieUtil;

    @GetMapping("/{kakaoId}")
    @Operation(summary = "회원 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserRes.class))),
            @ApiResponse(responseCode = "500", description = "회원 정보 조회 실패 - 존재하지 않는 계정")
    })
    public ResponseEntity<? extends UserRes> getUser(@PathVariable String kakaoId) {
        Auth auth = authService.isUser(kakaoId);
        if (auth == null) {
            throw new NotFoundException(kakaoId + " User Not Found");
        }
        User user = auth.getUser();
        if (user == null) {
            throw new NotFoundException(kakaoId + " User Not Found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(UserRes.of(2000, "Success", kakaoId, user));
    }

    @GetMapping("/room")
    @Operation(summary = "내가 참여한 방 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "참여한 방 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = RoomListRes.class))),
            @ApiResponse(responseCode = "500", description = "")
    })
    public ResponseEntity<? extends RoomListRes> getRooms(
            @RequestParam(value = "host") String host,
            HttpServletRequest request) {
        Long userId = cookieUtil.getUserIdByToken(request);
        List<Room> rooms = userService.getMyRooms(userId, Boolean.parseBoolean(host));

        User user = userService.getUserById(userId);
        List<Boolean> hostReviews = new ArrayList<>();
        List<Boolean> memberReviews = new ArrayList<>();

        for (Room room : rooms) {
            Boolean hostReview = null;
            Boolean memberReview = null;
            if (room.getReservationDate().compareTo(LocalDateTime.now()) < 0) { // 다음날인 경우
                hostReview = hostEvaluationService.checkHostEvaluation(user, room);
                memberReview = memberEvaluationService.checkMemberEvaluation(user, room);
            }
            hostReviews.add(hostReview);
            memberReviews.add(memberReview);
        }

        return ResponseEntity.status(HttpStatus.OK).body(RoomListRes.createMyRoomList(2000, "Success", rooms, hostReviews, memberReviews));
    }

    @GetMapping("/room/{roomId}/add")
    @Operation(summary = "방 입장하기")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "방 입장 성공",
                    content = @Content(schema = @Schema(implementation = BaseResponseBody.class))),
            @ApiResponse(responseCode = "500", description = "방 입장 실패")
    })
    public ResponseEntity<? extends BaseResponseBody> addRoom(@PathVariable String roomId, HttpServletRequest request) {
        Long userId = cookieUtil.getUserIdByToken(request);
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }

        Participation participation = userService.addMyRoom(userId, Long.valueOf(roomId));
        Room room = roomService.getRoom(Long.valueOf(roomId));

        try {
            // TODO: wallet 잔액 부족한 경우
            singleBungleService.enterRoom(room.getId(), userId, room.getHostId(), room.getContractAddress(), room.getPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseBody.of(2020, "Success"));
    }

    @GetMapping("/room/{roomId}/delete")
    @Operation(summary = "방 나가기")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "방 나가기 성공"),
            @ApiResponse(responseCode = "500", description = "방 나가기 실패")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteRoom(@PathVariable String roomId, HttpServletRequest request) {
        Long userId = cookieUtil.getUserIdByToken(request);
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }

        try {
            Room room = userService.deleteMyRoom(userId, Long.valueOf(roomId));
            singleBungleService.exitRoom(room.getId(), userId, room.getHostId(), room.getContractAddress(), room.getPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseBody.of(2020, "Success"));
    }

    @GetMapping("/room/{roomId}/withdraw")
    @Operation(summary = "방장이 출금하기")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "출금 성공"),
            @ApiResponse(responseCode = "401", description = "호스트가 아닙니다."),
            @ApiResponse(responseCode = "500", description = "출금 실패")
    })
    public ResponseEntity<? extends BaseResponseBody> withdraw(@PathVariable String roomId, HttpServletRequest request) {
        Room room = roomService.getRoom(Long.valueOf(roomId));
        if (room == null) {
            throw new NotFoundException("Room Not Found");
        }

        Long userId = cookieUtil.getUserIdByToken(request);
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }
        if (!room.getHostId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponseBody.of(4010, "Fail"));
        }

        try {
            singleBungleService.endRoom(room.getId(), room.getHostId(), room.getContractAddress());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseBody.of(2020, "Success"));
    }

    @GetMapping("/{kakaoId}/room")
    @Operation(summary = "완료된 모임 조회")
    public ResponseEntity<? extends RoomListRes> getMyCompleteRooms(
            @PathVariable String kakaoId,
            @RequestParam(value = "host") String host,
            HttpServletRequest request
    ) {
        User user = authService.isUser(kakaoId).getUser();
        if (user == null) {
            throw new NotFoundException("User Not Found");
        }

        List<Room> myRooms = userService.getMyRooms(user.getId(), Boolean.parseBoolean(host));
        List<Room> myCompleteRooms = new ArrayList<>();
        List<Integer> hostReviewResults = new ArrayList<>();
        List<ReviewRes> memberReviewResults = new ArrayList<>();

        for (Room room : myRooms) {
            if (room.getReservationDate().compareTo(LocalDateTime.now()) < 0) { // 예약일 다음날
                Boolean hostReview = hostEvaluationService.checkHostEvaluation(user, room);
                Boolean memberReview = memberEvaluationService.checkMemberEvaluation(user, room);

                if (hostReview && memberReview) { // 방장, 참여자 평가가 완료된 경우
                    int best = 0, good = 0, bad = 0;

                    myCompleteRooms.add(room);
                    hostReviewResults.add(hostEvaluationService.getSuccessEvaluation(room));

                    List<MemberEvaluation> memberEvaluations = memberEvaluationService.getMemberEvaluations(user, room);
                    for (MemberEvaluation memberEvaluation : memberEvaluations) {
                        Review review = memberEvaluation.getReview();
                        if (review.equals(Review.BEST)) {
                            best++;
                        } else if (review.equals(Review.GOOD)) {
                            good++;
                        } else if (review.equals(Review.BAD)) {
                            bad++;
                        }
                    }
                    memberReviewResults.add(ReviewRes.of(best, good, bad));
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(RoomListRes.createMyCompleteRoomList(
                2000, "Success", myCompleteRooms, hostReviewResults, memberReviewResults));
    }

}
