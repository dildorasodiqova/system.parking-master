package com.example.service;

import com.example.dto.ApiResponse;
import com.example.entity.CarEntity;
import com.example.enums.CarLocationStatus;
import com.example.enums.CarStatus;
import com.example.enums.PlaceAreaStatus;
import com.example.repository.CarLocationRepository;
import com.example.repository.CarRepository;
import com.example.repository.PlaceAreaRepository;
import com.example.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CarLocationService {
    private final PlaceAreaRepository placeAreaRepository;
    private final CarRepository carRepository;

    private final CarLocationRepository carLocationRepository;

    public ApiResponse<?> cancelParking(Long carLocationId) {
        log.info("Cancel parking = {}", carLocationId);
        Optional<CarEntity> carOpt = carRepository.findByCarLocationId(carLocationId);
        if (carOpt.isEmpty()) return ApiResponse.badRequest("Mashina topilmadi!");

        CarEntity car = carOpt.get();
        if (!car.getStatus().equals(CarStatus.LOCATING)) return ApiResponse.badRequest("Status xato!");

        carLocationRepository.updateStatusById(CarLocationStatus.CANCEL_LOCATING, carLocationId, LocalDateTime.now(), null);

        carRepository.updatePlaceIdByStatus(carLocationId, CarStatus.NOT_LOCATED);

        placeAreaRepository.updateStatusByCarLocationId(carLocationId, PlaceAreaStatus.AVAILABLE);

        return ApiResponse.success();
    }

    public ApiResponse<?> confirmParking(Long carLocationId) {
        log.info("Confirm parking = {}", carLocationId);

        Optional<CarEntity> carOpt = carRepository.findByCarLocationId(carLocationId);
        if (carOpt.isEmpty()) return ApiResponse.badRequest("Mashina topilmadi!");

        CarEntity car = carOpt.get();

        if (!car.getStatus().equals(CarStatus.LOCATING))
            return ApiResponse.badRequest("Status xato!");

        carLocationRepository.updateStatusById(CarLocationStatus.LOCATED, carLocationId, null, null);

        carRepository.updatePlaceIdByStatus(carLocationId, CarStatus.LOCATED);

        placeAreaRepository.updateStatusByCarLocationId(carLocationId, PlaceAreaStatus.NOT_AVAILABLE);

        return ApiResponse.success();
    }

    public ApiResponse<?> takeCarFromParking(Long carLocationId) {

        Optional<CarEntity> carOpt = carRepository.findByCarLocationId(carLocationId);
        if (carOpt.isEmpty()) return ApiResponse.badRequest("Mashina topilmadi!");

        CarEntity car = carOpt.get();
        if (!car.getStatus().equals(CarStatus.LOCATED)) return ApiResponse.badRequest("Status xato!");

        carLocationRepository.updateStatusById(CarLocationStatus.TAKEN, carLocationId, null, LocalDateTime.now());
        carRepository.updatePlaceIdByStatus(carLocationId, CarStatus.TAKEN);
        placeAreaRepository.updateStatusByCarLocationId(carLocationId, PlaceAreaStatus.AVAILABLE);
        return ApiResponse.success();
    }
}
