package com.sgbg.service;

import com.sgbg.domain.HostEvaluation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import com.sgbg.repository.HostEvaluationRepository;
import com.sgbg.service.interfaces.IHostEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HostEvaluationService implements IHostEvaluationService {

    @Autowired
    HostEvaluationRepository hostEvaluationRepository;

    @Override
    public HostEvaluation createEvaluation(User user, Room room, Boolean isSuccess, Long transactionId) {
        HostEvaluation hostEvaluation = HostEvaluation.builder()
                .user(user)
                .room(room)
                .isSuccess(isSuccess)
                .transactionId(transactionId)
                .build();

        hostEvaluation.addHostEvaluation(user, room);
        hostEvaluationRepository.save(hostEvaluation);
        return hostEvaluation;
    }

    @Override
    public Boolean checkHostEvaluation(User user, Room room) {
        HostEvaluation hostEvaluation = hostEvaluationRepository.findHostEvaluationByUserAndRoom(user, room).orElse(null);
        return hostEvaluation != null;
    }

    @Override
    public int getSuccessEvaluation(Room room) {
        List<HostEvaluation> hostEvaluationsByRoomAndIsSuccess = hostEvaluationRepository.findHostEvaluationsByRoomAndIsSuccess(room, true);
        return hostEvaluationsByRoomAndIsSuccess.size();
    }
}
