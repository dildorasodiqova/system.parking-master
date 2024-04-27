package com.example.entity;

import com.example.enums.CarStatus;
import com.example.repository.PlaceRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Getter
@Setter
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String model;
    private String color;
    private String position;  // pozitsiya
    private String type; // sport
    private String imageUrl;

    private String recipientName;
    private Long recipientRegionId;

    @Column(name = "place_id")
    private Long placeId; // PlaceRegionId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", insertable = false, updatable = false)
    private PlaceAreaEntity placeArea;

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private CarStatus status;
}