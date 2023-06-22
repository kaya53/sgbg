package com.sgbg.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public Participation(Room room, User user) {
        this.room = room;
        this.user = user;
    }

    public void addMember(User user, Room room) {
        user.getMyRooms().add(this);
        room.getMembers().add(this);
    }

    public void deleteMember(User user, Room room) {
        user.getMyRooms().remove(this);
        room.getMembers().remove(this);
    }
}
