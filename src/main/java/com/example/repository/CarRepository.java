
package com.example.repository;

import com.example.entity.CarEntity;
import com.example.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
    @Transactional
    @Modifying
    @Query("update CarEntity c set c.status = ?2 where c.id = (select cl.carId from CarLocationEntity cl where cl.id = ?1)")
    void updatePlaceIdByStatus(Long carLocationId, CarStatus status);

    @Query("""
            select c from CarEntity c
            left join fetch c.placeArea
            where c.number = ?1
            """)
    Optional<CarEntity> findByNumber(String number);

    @Transactional
    @Modifying
    @Query("update CarEntity c set c.status = ?1,c.placeId = ?3 where c.id = ?2")
    void updateStatusById(CarStatus status, Long id, Long placeId);
    @Query("from CarEntity c where c.id in (select cl.carId from CarLocationEntity cl where cl.id = ?1)")
    Optional<CarEntity> findByCarLocationId(Long carLocationId);
}