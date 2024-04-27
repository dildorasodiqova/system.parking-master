package com.example.dto;

import com.example.entity.CarEntity;
import com.example.entity.CarLocationEntity;
import com.example.enums.CarStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Getter
@Setter
public class CarDto {

    private Long id;
    private String number;
    private String model;
    private String color;
    private String position;
    private String type;
    private String imageUrl;
    private CarStatus status;
    private CarLocationResDTO location;

    public CarDto() {
    }

    public CarDto(Long id, String number, String model, String color, String position, String type, String imageUrl, CarStatus status, CarLocationResDTO location) {
        this.id = id;
        this.number = number;
        this.model = model;
        this.color = color;
        this.position = position;
        this.type = type;
        this.imageUrl = imageUrl;
        this.status = status;
        this.location = location;
    }

    public CarDto(CarEntity car) {
        this.id = car.getId();
        this.number = car.getNumber();
        this.model = car.getModel();
        this.color = car.getColor();
        this.position = car.getPosition();
        this.type = car.getType();
        this.imageUrl = car.getImageUrl();
        this.status = car.getStatus();
    }

    public static CarDto toDTO(CarEntity car, CarLocationEntity carLocation,PlaceAreaResDTO place) {
        return new CarDto(
                car.getId(),
                car.getNumber(),
                car.getModel(), car.getColor(), car.getPosition(),
                car.getType(), car.getImageUrl(),
                car.getStatus(),
                CarLocationResDTO.toDTO(carLocation, place)
        );
    }

    // Getters and setters
}
