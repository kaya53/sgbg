package com.sgbg.api.response;

import com.sgbg.domain.Location;
import com.sgbg.domain.Participation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BaseRoomRes {
    @Schema(name = "roomId")
    private Long roomId;

    @Schema(name = "hostId")
    private Long hostId;

    @Schema(name = "hostName")
    private String hostName;

    @Schema(name = "parentCategory", example = "이색놀거리")
    private String parentCategory;

    @Schema(name = "childCategory", example = "보드게임")
    private String childCategory;

    @Schema(name = "title", example = "보드게임 고수만")
    private String title;

    @Schema(name = "maxUser", example = "10")
    private Long maxUser;

    @Schema(name = "minUser", example = "2")
    private Long minUser;

    @Schema(name = "price", example = "50000")
    private Long price;

    @Schema(name = "location", example = "서울시 멀티캠퍼스")
    private Location location;

    @Schema(name = "reservationDate", example = "2022-12-25T20:00:00")
    private LocalDateTime reservationDate;

    @Schema(name = "endDate", example = "2022-12-20T20:00:00")
    private LocalDateTime endDate;

    @Schema(name = "minMemberScore", example = "35.4")
    private Double minMemberScore;

    @Schema(name = "description", example = "모여서 인생 걸고 보드게임 하실분 모집합니다. 할리갈리 선출입니다.")
    private String description;

    @Schema(name = "members")
    private List<BaseUserRes> members = new ArrayList<>();

    @Schema(name = "hostReview")
    private Boolean hostReview = null;

    @Schema(name = "memberReview")
    private Boolean memberReview = null;

    @Schema(name = "hostReviewResult")
    private int hostReviewResult;

    @Schema(name = "memberReviewResult")
    private ReviewRes memberReviewResult;

    public static BaseRoomRes of(Room room) {
        BaseRoomRes baseRoomRes = new BaseRoomRes();
        baseRoomRes.setRoomId(room.getId());
        baseRoomRes.setTitle(room.getTitle());
        baseRoomRes.setHostName(room.getHostName());
        baseRoomRes.setParentCategory(room.getParentCategory());
        baseRoomRes.setChildCategory(room.getChildCategory());
        baseRoomRes.setMaxUser(room.getMaxUser());
        baseRoomRes.setMinUser(room.getMinUser());
        baseRoomRes.setPrice(room.getPrice());
        baseRoomRes.setLocation(room.getLocation());
        baseRoomRes.setReservationDate(room.getReservationDate());
        baseRoomRes.setEndDate(room.getEndDate());
        baseRoomRes.setMinMemberScore(room.getMinMemberScore());
        baseRoomRes.setDescription(room.getDescription());
        baseRoomRes.setMembers(room.getMembers());
        return baseRoomRes;
    }

    public static BaseRoomRes createMyRoomRes(Room room, Boolean hostReview, Boolean memberReview) {
        BaseRoomRes res = BaseRoomRes.of(room);
        res.setHostReview(hostReview);
        res.setMemberReview(memberReview);
        return res;
    }

    public static BaseRoomRes createMyCompleteRoomRes(Room room, int hostReviewResult, ReviewRes memberReviewResult) {
        BaseRoomRes res = BaseRoomRes.of(room);
        res.setHostReviewResult(hostReviewResult);
        res.setMemberReviewResult(memberReviewResult);
        return res;
    }

    public void setMembers(List<Participation> members) {
        for(Participation participation: members) {
            User member = participation.getUser();
            this.members.add(BaseUserRes.of(member, String.valueOf(member.getAuth().getKakaoNumber())));
        }
    }
}
