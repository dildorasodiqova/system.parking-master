package com.example.entity;

import com.example.enums.CarLocationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyGroup;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Entity
@Table(name = "car_location")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarLocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long carId;

    @Column(name = "place_id")
    private Long placeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", insertable = false, updatable = false)
    private PlaceAreaEntity placeArea;

    @Enumerated(EnumType.STRING)
    private CarLocationStatus status;

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdDate;

    private LocalDateTime dateOfReceipt;

    private LocalDateTime canceledDate;

    // birinchi yaratilganda joylashtirilmoqda statusda boladi
    public CarLocationEntity(Long carId, Long placeId) {
        this.carId = carId;
        this.placeId = placeId;
        this.status = CarLocationStatus.LOCATING;
    }
}
