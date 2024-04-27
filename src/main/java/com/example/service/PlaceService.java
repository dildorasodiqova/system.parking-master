package com.example.service;

import com.example.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
}
