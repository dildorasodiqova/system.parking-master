package com.example.dto;

import com.example.entity.PlaceAreaEntity;
import com.example.enums.PlaceAreaStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceAreaResDTO {
    private Long id;
    private Long regionId;
    private String title;
    private Double latitude;
    private Double longitude;
    private PlaceAreaStatus status;

    public PlaceAreaResDTO(PlaceAreaEntity item) {
        this.id = item.getId();
        this.regionId = item.getRegionId();
        this.title = item.getName();
        this.status = item.getStatus();
        this.latitude = item.getLatitude();
        this.longitude = item.getLongitude();
    }
}
