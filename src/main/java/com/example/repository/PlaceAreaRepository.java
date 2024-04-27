package com.example.repository;

import com.example.entity.PlaceAreaEntity;
import com.example.enums.PlaceAreaStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PlaceAreaRepository extends JpaRepository<PlaceAreaEntity, Long> {


    @Query(value = "select p from place_area p where p.status = ?2 and p.region_id = ?1 ",nativeQuery = true)
    Page<PlaceAreaEntity> findByRegionIdAndStatus(Long regionId, PlaceAreaStatus status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update PlaceAreaEntity p set p.status = ?1 where p.id = ?2")
    void updateStatusById(PlaceAreaStatus status, Long placeId);

    @Query("update PlaceAreaEntity p set p.status = ?2 where p.id in (select cl.placeId from CarLocationEntity cl where cl.id = ?1)")
    @Modifying
    @Transactional
    void updateStatusByCarLocationId(Long carLocationId, PlaceAreaStatus status);
}