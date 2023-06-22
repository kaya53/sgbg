package com.sgbg.repository;

import com.sgbg.domain.Participation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import com.sgbg.repository.interfaces.ParticipationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long>, ParticipationRepositoryCustom {

//    Optional<Participation> findParticipationByUser(User user);

    Optional<Participation> findParticipationByUserAndRoom(User user, Room room);

}
