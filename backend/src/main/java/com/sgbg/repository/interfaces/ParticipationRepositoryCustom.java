package com.sgbg.repository.interfaces;

import com.sgbg.domain.Room;
import com.sgbg.domain.User;

import java.util.List;

public interface ParticipationRepositoryCustom {

    List<Room> findMyRoomsByUserId(Long userId);

//    List<User> findMembersByRoomId(Long roomId);
}
