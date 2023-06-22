package com.sgbg.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Room")
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "room_id")
    private Long id;

    @Column(name = "parent_category")
    private String parentCategory;

    @Column(name = "child_category")
    private String childCategory;

    @Column(name = "host_id")
    private Long hostId;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "title")
    private String title;

    @Column(name = "min_user")
    @ColumnDefault(value = "2")
    private Long minUser;

    @Column(name = "max_user")
    @ColumnDefault(value = "2")
    private Long maxUser;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "min_member_score")
    private Double minMemberScore;

    @Column(name = "description")
    private String description;

    @Column(name = "contract_address")
    private String contractAddress;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Participation> members = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<HostEvaluation> hostEvaluations = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<MemberEvaluation> memberEvaluations = new ArrayList<>();

    @Builder
    public Room(String parentCategory, String childCategory,
                Long hostId, String hostName, String title, Long minUser, Long maxUser,
                Long price, Location location, LocalDateTime reservationDate, LocalDateTime createdDate,
                LocalDateTime endDate, Double minMemberScore, String description, String contractAddress) {

        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.hostId = hostId;
        this.hostName = hostName;
        this.title = title;
        this.minUser = minUser;
        this.maxUser = maxUser;
        this.price = price;
        this.location = location;
        this.reservationDate = reservationDate;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.minMemberScore = minMemberScore;
        this.description = description;
        this.contractAddress = contractAddress;
    }
}
