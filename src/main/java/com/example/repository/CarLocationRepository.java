
package com.example.repository;

import com.example.entity.CarLocationEntity;
import com.example.enums.CarLocationStatus;
import com.example.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CarLocationRepository extends JpaRepository<CarLocationEntity, Long> {
    @Transactional
    @Modifying
    @Query("update CarLocationEntity c set c.status = ?1,c.canceledDate = ?3,c.dateOfReceipt = ?4 where c.id = ?2")
    void updateStatusById(CarLocationStatus status, Long id, LocalDateTime cancelDate,LocalDateTime dateOfReceipt );

    @Query("select c from CarLocationEntity c inner join CarEntity car on car.id = c.carId left join fetch c.placeArea where car.status in (?3) and  c.carId = ?1 and c.placeId = ?2 order by c.createdDate desc limit 1")
    Optional<CarLocationEntity> findByCarIdAndPlaceId(Long carId, Long placeId, List<CarStatus> carsStatuses);
}