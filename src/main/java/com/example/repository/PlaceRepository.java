
package com.example.repository;

import com.example.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {
}