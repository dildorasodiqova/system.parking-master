package com.example.dto;

import com.example.entity.CarLocationEntity;
import com.example.enums.CarLocationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarLocationResDTO {
    private Long id;
    private CarLocationStatus status;
    private PlaceAreaResDTO place;
    private LocalDateTime createdDate;
    private LocalDateTime canceledDate;
    private LocalDateTime dateOfReceipt;


    public static CarLocationResDTO toDTO(CarLocationEntity location) {
        if (location == null) return null;
        return new CarLocationResDTO(
                location.getId(),
                location.getStatus(),
                new PlaceAreaResDTO(location.getPlaceArea()),
                location.getCreatedDate(),
                location.getCanceledDate(),
                location.getDateOfReceipt()
        );
    }

    public static CarLocationResDTO toDTO(CarLocationEntity location, PlaceAreaResDTO place) {
        if (location == null) return null;
        return new CarLocationResDTO(
                location.getId(),
                location.getStatus(),
                place,
                location.getCreatedDate(),
                location.getCanceledDate(),
                location.getDateOfReceipt()
        );
    }
}
