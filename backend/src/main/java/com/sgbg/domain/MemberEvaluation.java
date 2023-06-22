package com.sgbg.domain;

import com.sgbg.api.request.Review;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class MemberEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_evaluation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Review review;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id", referencedColumnName = "user_id")
    private User evaluator; // 평가하는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public MemberEvaluation(int score, User evaluator, User user, Room room, Review review) {
        this.score = score;
        this.evaluator = evaluator;
        this.user = user;
        this.room = room;
        this.review = review;
    }

    public void addMemberEvaluation(Room room, User evaluator, User user) {
        room.getMemberEvaluations().add(this);
        evaluator.getMyMemberEvaluations().add(this);
        user.getMyMemberEvaluationResults().add(this);
    }
}
