package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.CardAddDTO;
import com.example.service.CarLocationService;
import com.example.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Admin on 23.04.2024
 * @project system.parking
 * @package com.example.controller
 * @contact @sarvargo
 */
@RestController
@RequestMapping("/parking")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Car Location controller")
public class CarLocationController {
    private final CarLocationService carLocationService;
    private final CarService carService;

    @PutMapping("/cancel/{carLocationId}")
    @Operation(
            summary = "Joyga qoyayotgan mashinani cancel qilish.",
            description = "Mashinani joyiga qoyayotgada qoydizmi deb chiqsa yoq ni bosaganda murojat qilish kere",
            parameters = {
                    @Parameter(name = "carLocationId", description = "car ni ichidagi location ni idsini beras.")
            }
    )
    public ResponseEntity<ApiResponse<?>> cancelParking(@PathVariable("carLocationId") Long carLocationId) {
        return ResponseEntity.ok(carLocationService.cancelParking(carLocationId));
    }

    @PutMapping("/confirm/{carLocationId}")
    @Operation(
            summary = "Joyiga qo'yganligini tasdiqlash uchun!",
            parameters = {
                    @Parameter(name = "carLocationId", description = "car ni ichidagi location ni idsini beras.")
            }
    )
    public ResponseEntity<ApiResponse<?>> confirmParking(@PathVariable("carLocationId") Long carLocationId) {
        return ResponseEntity.ok(carLocationService.confirmParking(carLocationId));
    }

    // mashinani olib ketilgandan keyin
    @PutMapping("/take/{carLocationId}")
    @Operation(
            summary = "Mashinani parkovkadan olib ketgandan keyin olib kettim deb belgilab qoyish"
    )
    public ResponseEntity<ApiResponse<?>> takeCarFromParking(@PathVariable("carLocationId") Long carLocationId) {
        log.info("Take from parking id={}",carLocationId);
        return ResponseEntity.ok(carLocationService.takeCarFromParking(carLocationId));
    }

    @GetMapping("/suggestion-place")
    @Operation(
            summary = "Mashinani kiritilgan code/number orqali tavsiya etilgan joylarni olish!"
    )
    public ResponseEntity<ApiResponse<?>> findSuggestionPlace(@RequestParam("number") String number,
                                                              @RequestParam("latitude") Double latitude,
                                                              @RequestParam("longitude") Double longitude,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "20") int size) {
        log.info("Get suggestion places number={}", number);
        return ResponseEntity.ok(carService.getSuggestionPlaces(number, latitude, longitude, page, size));
    }

    @PostMapping("/add")
    @Operation(
            summary = "Mashinani qaysidur joyga qoyyapman deb belgilash!"
    )
    public ResponseEntity<ApiResponse<?>> addToPlace(@RequestBody CardAddDTO dto) {
        log.info("Car add = {}", dto);
        return ResponseEntity.ok(carService.add(dto));
    }
}
