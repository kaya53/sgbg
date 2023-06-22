package com.sgbg.domain;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    @NotNull
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
