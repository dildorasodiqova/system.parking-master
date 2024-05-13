package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.CarDto;
import com.example.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Tag(name = "Car controller")
public class CarController {

    private final CarService carService;

    @GetMapping("/find")
    @Operation(
            summary = "Kiritilgan yoki scan qilingan number bilan avtamobilni topish"
    )
    public ResponseEntity<ApiResponse<CarDto>> findByNumber(@RequestParam("number") String number) {
        log.info("Find by number = {}", number);
        return ResponseEntity.ok(carService.getByNumber(number));
    }

    @GetMapping("/find-for-add")
    @Operation(
            summary = "Kiritilgan yoki scan qilingan number bilan avtamobilni topish va osha joyga yonaltirish"
    )
    public ResponseEntity<ApiResponse<CarDto>> findByNumberForAdd(@RequestParam("number") String number,
                                                                  @RequestParam("latitude") Double latitude,
                                                                  @RequestParam("longitude") Double longitude) {
        log.info("Find by number = {}", number);
        return ResponseEntity.ok(carService.getByNumberForAdd(number, latitude, longitude));
    }


}
