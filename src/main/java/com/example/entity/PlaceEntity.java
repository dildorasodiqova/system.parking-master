package com.example.entity;

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
@Table(name = "place")
@Getter
@Setter
@SQLRestriction("visible = true")
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdDate;

    private Boolean visible = true;

}
