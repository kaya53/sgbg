package com.sgbg.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    private String name;

    private String email;

    private LocalDateTime createdAt;

    private int hostScore;

    private int memberScore;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Auth auth;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Participation> myRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<HostEvaluation> myHostEvaluationResults = new ArrayList<>(); // 나(방장)를 평가한 결과들

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MemberEvaluation> myMemberEvaluationResults = new ArrayList<>(); // 나(참여자)를 평가한 결과들

    @OneToMany(mappedBy = "evaluator", fetch = FetchType.LAZY)
    private List<MemberEvaluation> myMemberEvaluations = new ArrayList<>(); // 내가 참여자를 평가한 내역들

    @Builder
    public User(String name, String email, int hostScore, int memberScore) {
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.hostScore = hostScore;
        this.memberScore = memberScore;
    }
}
