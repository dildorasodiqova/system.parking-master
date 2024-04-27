package com.example.entity;

import com.example.enums.PlaceAreaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Entity
@Table(name = "place_area")
@Getter
@Setter
public class PlaceAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long regionId;

    private String name;

    private double latitude = 0;

    private double longitude = 0;

    @Enumerated(EnumType.STRING)
    private PlaceAreaStatus status;

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdDate;

    private Boolean visible = true;
}
