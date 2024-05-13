package com.example.service;

import com.example.dto.*;
import com.example.entity.CarEntity;
import com.example.entity.CarLocationEntity;
import com.example.entity.PlaceAreaEntity;
import com.example.enums.CarStatus;
import com.example.enums.PlaceAreaStatus;
import com.example.exp.ResourceNotFoundException;
import com.example.repository.CarLocationRepository;
import com.example.repository.CarRepository;
import com.example.repository.PlaceAreaCustomRepository;
import com.example.repository.PlaceAreaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarLocationRepository carLocationRepository;

    private final CarRepository carRepository;

    private final PlaceAreaRepository placeAreaRepository;

    private final PlaceAreaCustomRepository placeAreaCustomRepository;

    public List<CarDto> getAllCars() {
        List<CarEntity> cars = carRepository.findAll();
        return cars.stream().map(CarDto::new).collect(Collectors.toList());
    }

    public CarDto getCarById(Long id) throws ResourceNotFoundException {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return new CarDto(car);
    }

    public CarDto createCar(CarDto carDto) {
        CarEntity car = new CarEntity();
        car.setNumber(carDto.getNumber());
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setPosition(carDto.getPosition());
        car.setType(carDto.getType());
        car.setImageUrl(carDto.getImageUrl());
        carRepository.save(car);
        return new CarDto(car);
    }

    public CarDto updateCar(Long id, CarDto carDto) throws ResourceNotFoundException {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        car.setNumber(carDto.getNumber());
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setPosition(carDto.getPosition());
        car.setType(carDto.getType());
        car.setImageUrl(carDto.getImageUrl());
        carRepository.save(car);
        return new CarDto(car);
    }


    public ApiResponse<?> add(CardAddDTO dto) {
        Optional<CarEntity> carOpt = carRepository.findById(dto.getCardId());

        if (carOpt.isEmpty()) return ApiResponse.badRequest("Mashina topilmadi!");

        CarEntity car = carOpt.get();
        if (!car.getStatus().equals(CarStatus.NOT_LOCATED)) return ApiResponse.badRequest("Status xato");

        carLocationRepository.save(new CarLocationEntity(dto.getCardId(), dto.getPlaceId()));

        placeAreaRepository.updateStatusById(PlaceAreaStatus.PARKING, dto.getPlaceId());

        carRepository.updateStatusById(CarStatus.LOCATING, dto.getCardId(), dto.getPlaceId());

        return ApiResponse.success();
    }

    public ApiResponse<CarDto> addBefore(CardAddDTO dto, PlaceAreaResDTO place) {
        Optional<CarEntity> carOpt = carRepository.findById(dto.getCardId());

        if (carOpt.isEmpty()) return ApiResponse.badRequest("Mashina topilmadi!");

        CarEntity car = carOpt.get();
        if (!car.getStatus().equals(CarStatus.NOT_LOCATED)) return ApiResponse.badRequest("Status xato");

        CarLocationEntity save = carLocationRepository.save(new CarLocationEntity(dto.getCardId(), dto.getPlaceId()));

        placeAreaRepository.updateStatusById(PlaceAreaStatus.PARKING, dto.getPlaceId());

        carRepository.updateStatusById(CarStatus.LOCATING, dto.getCardId(), dto.getPlaceId());

        car.setStatus(CarStatus.LOCATING);
        place.setStatus(PlaceAreaStatus.PARKING);

        return ApiResponse.success(
                CarDto.toDTO(car, save, place)
        );
    }

    public void deleteCarById(Long id) throws ResourceNotFoundException {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
    }

    public ApiResponse<CarDto> getByNumber(String number) {
        return carRepository.findByNumber(number)
                .map(item -> {
                    CarDto carDto = new CarDto(item);
                    Optional<CarLocationEntity> carLocationOpt = carLocationRepository.findByCarIdAndPlaceId(item.getId(), item.getPlaceId(), List.of(CarStatus.LOCATED, CarStatus.LOCATING, CarStatus.TAKEN));
                    carLocationOpt.ifPresent(carLocationEntity -> carDto.setLocation(CarLocationResDTO.toDTO(carLocationEntity)));
                    return ApiResponse.success(carDto);
                })
                .orElseGet(() -> ApiResponse.badRequest("Bunday avtomobil topilmadi!"));
    }

    public ApiResponse<List<PlaceAreaResDTO>> getSuggestionPlaces(String number, Double latitude, Double longitude, int page, int size) {
        Optional<CarEntity> carOpt = carRepository.findByNumber(number);
        if (carOpt.isEmpty()) return ApiResponse.badRequest("Bunday avtomobil topilmadi!");
        List<PlaceAreaEntity> nearbyPlaces = placeAreaCustomRepository.findNearbyPlaces(carOpt.get().getRecipientRegionId(), latitude, longitude, page, size);
        return ApiResponse.success(
                nearbyPlaces
                        .stream()
                        .map(PlaceAreaResDTO::new)
                        .toList()
        );
    }

    public ApiResponse<CarDto> getByNumberForAdd(String number, Double latitude, Double longitude) {
        Optional<CarEntity> carOpt = carRepository.findByNumber(number);
        if (carOpt.isEmpty()) return ApiResponse.badRequest("Bunday avtomobil topilmadi!");

        ApiResponse<List<PlaceAreaResDTO>> suggestionPlaces = getSuggestionPlaces(number, latitude, longitude, 0, 1);

        if (suggestionPlaces.getData().isEmpty()) return ApiResponse.badRequest("Taklif qilingan joylar yo'q");

        List<PlaceAreaResDTO> suggestionPlacesList = suggestionPlaces.getData();
        CarEntity car = carOpt.get();
        PlaceAreaResDTO areaResDTO = suggestionPlacesList.get(0);

        ApiResponse<CarDto> apiResponse = addBefore(new CardAddDTO(car.getId(), areaResDTO.getId()), areaResDTO);
        if (!apiResponse.getIsSuccess()) return ApiResponse.badRequest(apiResponse.getMessage());
        return apiResponse;
    }
}
