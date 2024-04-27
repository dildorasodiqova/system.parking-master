package com.example.repository;

import com.example.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
}