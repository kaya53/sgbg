package com.sgbg.api.request;

import com.sgbg.domain.Location;
import com.sgbg.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class RoomReq {

    @Schema(name = "방장 아이디")
    private Long hostId;

    @Schema(name = "방장 닉네임")
    private String hostName;

    @Schema(name = "대분류 카테고리", example = "이색놀거리")
    private String parentCategory;

    @Schema(name = "소분류 카테고리", example = "보드게임")
    private String childCategory;

    @Schema(name = "방 제목", example = "보드게임 고수만")
    private String title;

    @Schema(name = "최대 참여 유저", example = "10")
    private Long maxUser;

    @Schema(name = "최소 참여 유저", example = "2")
    private Long minUser;

    @Schema(name = "모임 참여 비용", example = "50000")
    private Long price;

    @Schema(name = "모임 장소", example = "서울시 멀티캠퍼스")
    private Location location;

    @Schema(name = "모임 날짜", example = "2022-12-25T20:00:00")
    private LocalDateTime reservationDate;

    @Schema(name = "모집 마감 날짜", example = "2022-12-20T20:00:00")
    private LocalDateTime endDate;

    @Schema(name = "참여 가능 참여자 점수", example = "84")
    private Double minMemberScore;

    @Schema(name = "방 정보(설명)", example = "모여서 인생 걸고 보드게임 하실분 모집합니다. 할리갈리 선출입니다.")
    private String description;

    public static RoomReq createRoomReq(String title, String parentCategory,
                   String hostName, String childCategory, Long maxUser,
                   Long minUser, Long price, Location location,
                   LocalDateTime reservationDate, LocalDateTime endDate,
                   Double minMemberScore, String description) {
        RoomReq roomReq = new RoomReq();
        roomReq.setTitle(title);
        roomReq.setParentCategory(parentCategory);
        roomReq.setChildCategory(childCategory);
        roomReq.setMaxUser(maxUser);
        roomReq.setMinUser(minUser);
        roomReq.setPrice(price);
        roomReq.setLocation(location);
        roomReq.setReservationDate(reservationDate);
        roomReq.setEndDate(endDate);
        roomReq.setMinMemberScore(minMemberScore);
        roomReq.setDescription(description);

        return roomReq;

    }

    public Room toEntity(RoomReq roomReq){

        return Room.builder()
                .title(roomReq.title)
                .parentCategory(roomReq.parentCategory)
                .childCategory(roomReq.childCategory)
                .maxUser(roomReq.maxUser)
                .minUser(roomReq.minUser)
                .price(roomReq.price)
                .location(roomReq.location)
                .reservationDate(roomReq.reservationDate)
                .createdDate(LocalDateTime.now())
                .endDate(roomReq.endDate)
                .minMemberScore(roomReq.minMemberScore)
                .description(roomReq.description)
                .build();
    }
}
