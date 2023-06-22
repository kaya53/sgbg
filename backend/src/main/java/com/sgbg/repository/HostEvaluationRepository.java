package com.sgbg.repository;

import com.sgbg.domain.HostEvaluation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HostEvaluationRepository extends JpaRepository<HostEvaluation, Long> {

    Optional<HostEvaluation> findHostEvaluationByUserAndRoom(User user, Room room);

    List<HostEvaluation> findHostEvaluationsByRoomAndIsSuccess(Room room, Boolean isSuccess);

}
