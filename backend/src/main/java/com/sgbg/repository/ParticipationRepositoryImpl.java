package com.sgbg.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sgbg.domain.QParticipation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import com.sgbg.repository.interfaces.ParticipationRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sgbg.domain.QRoom.room;
import static com.sgbg.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class ParticipationRepositoryImpl implements ParticipationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    QParticipation participation = QParticipation.participation;

    @Override
    public List<Room> findMyRoomsByUserId(Long userId) {
        return jpaQueryFactory
                .select(participation.room)
                .from(participation)
                .join(participation.room, room)
                .where(participation.user.id.eq(userId))
                .orderBy(
                        participation.room.createdDate.desc()
                )
                .fetch();
    }

//    @Override
//    public List<User> findMembersByRoomId(Long roomId) {
//        return jpaQueryFactory
//                .select(participation.user)
//                .from(participation)
//                .join(participation.user, user)
//                .where(participation.room.id.eq(roomId), participation.isParticipate.eq(true))
//                .fetch();
//    }
}
