package com.sgbg.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "location")
@DynamicUpdate
@DynamicInsert
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @Column(name = "location_id")
    private String locationId;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "hardness")
    private String hardness;

    @Column(name = "road_address")
    private String roadAddress;

    @Builder
    public Location(String locationId, String name, String latitude, String hardness, String roadAddress) {
        this.locationId = locationId;
        this.name = name;
        this.latitude = latitude;
        this.hardness = hardness;
        this.roadAddress = roadAddress;

    }







}
