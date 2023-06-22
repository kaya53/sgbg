package com.sgbg.service.interfaces;

import com.sgbg.domain.HostEvaluation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;

public interface IHostEvaluationService {

    HostEvaluation createEvaluation(User user, Room room, Boolean isSuccess, Long transactionId);

    Boolean checkHostEvaluation(User user, Room room);

    int getSuccessEvaluation(Room room);
}
