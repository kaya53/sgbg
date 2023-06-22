package com.sgbg.service;

import com.sgbg.api.request.BaseMemberEvaluationReq;
import com.sgbg.domain.MemberEvaluation;
import com.sgbg.domain.Room;
import com.sgbg.domain.User;
import com.sgbg.repository.MemberEvaluationRepository;
import com.sgbg.service.interfaces.IMemberEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberEvaluationService implements IMemberEvaluationService {

    @Autowired
    MemberEvaluationRepository memberEvaluationRepository;

    @Override
    public MemberEvaluation createEvaluation(BaseMemberEvaluationReq memberEvaluationReq, Room room, User evaluator, User user) {
        MemberEvaluation memberEvaluation = MemberEvaluation.builder()
                .room(room)
                .evaluator(evaluator)
                .user(user)
                .review(memberEvaluationReq.getReview())
                .score(memberEvaluationReq.getReview().getScore())
                .build();

        memberEvaluation.addMemberEvaluation(room, evaluator, user);
        memberEvaluationRepository.save(memberEvaluation);

        return memberEvaluation;
    }

    @Override
    public Boolean checkMemberEvaluation(User evaluator, Room room) {
        List<MemberEvaluation> memberEvaluations = memberEvaluationRepository.findMemberEvaluationsByEvaluatorAndRoom(evaluator, room);

        return memberEvaluations.size() != 0;
    }

    @Override
    public List<MemberEvaluation> getMemberEvaluations(User user, Room room) {
        return memberEvaluationRepository.findMemberEvaluationsByUserAndRoom(user, room);
    }
}
