package com.sgbg.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class HostEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_evaluation_id")
    private Long id;

    private Boolean isSuccess;

    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 평가 받는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public HostEvaluation(Boolean isSuccess, Long transactionId, User user, Room room) {
        this.isSuccess = isSuccess;
        this.transactionId = transactionId;
        this.user = user;
        this.room = room;
    }

    public void addHostEvaluation(User user, Room room) {
        user.getMyHostEvaluationResults().add(this);
        room.getHostEvaluations().add(this);
    }
}
