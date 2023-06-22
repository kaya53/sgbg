package com.sgbg.api.response;

import com.sgbg.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoomListRes extends BaseResponseBody {

    @Schema(name = "roomListInfo", description = "방 목록 정보")
    private List<BaseRoomRes> roomListInfo = new ArrayList<>();

    public static RoomListRes of(Integer statusCode, String message, List<Room> roomList) {
        RoomListRes res = new RoomListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setRoomListInfo(roomList);
        return res;
    }

    public static RoomListRes createMyRoomList(Integer statusCode, String message, List<Room> roomList, List<Boolean> hostReviews, List<Boolean> memberReviews) {
        RoomListRes res = new RoomListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setMyRoomListInfo(roomList, hostReviews, memberReviews);
        return res;
    }

    public static RoomListRes createMyCompleteRoomList(Integer statusCode, String message, List<Room> roomList, List<Integer> hostReviewResults, List<ReviewRes> memberReviewResults) {
        RoomListRes res = new RoomListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setMyCompleteRoomListInfo(roomList, hostReviewResults, memberReviewResults);
        return res;
    }

    public void setRoomListInfo(List<Room> roomList) {
        for (Room room : roomList) {
            this.roomListInfo.add(BaseRoomRes.of(room));
        }
    }

    public void setMyRoomListInfo(List<Room> roomList, List<Boolean> hostReviews, List<Boolean> memberReviews) {
        for (int i = 0; i < roomList.size(); i++) {
            this.roomListInfo.add(
                    BaseRoomRes.createMyRoomRes(roomList.get(i), hostReviews.get(i), memberReviews.get(i))
            );
        }
    }

    public void setMyCompleteRoomListInfo(List<Room> roomList, List<Integer> hostReviewResults, List<ReviewRes> memberReviewResults) {
        for (int i = 0; i < roomList.size(); i++) {
            this.roomListInfo.add(
                    BaseRoomRes.createMyCompleteRoomRes(roomList.get(i), hostReviewResults.get(i), memberReviewResults.get(i))
            );
        }
    }
}
