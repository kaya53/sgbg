package com.sgbg.api.response;

import com.sgbg.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RoomRes extends BaseResponseBody {

    @Schema(name = "roomInfo", description = "방 정보")
    BaseRoomRes roomInfo;

    public static RoomRes of(Integer statusCode, String message, Room room) {
        RoomRes res = new RoomRes();
        res.setRoomInfo(room);
        res.setStatusCode(statusCode);
        res.setMessage(message);
        return res;
    }

    public void setRoomInfo(Room room) {
        this.roomInfo = BaseRoomRes.of(room);
    }
}
