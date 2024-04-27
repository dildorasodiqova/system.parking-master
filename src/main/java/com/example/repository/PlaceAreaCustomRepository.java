package com.example.repository;

import com.example.entity.PlaceAreaEntity;
import com.example.enums.PlaceAreaStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 23.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Repository
public class PlaceAreaCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PlaceAreaEntity> findNearbyPlaces(Long regionId, double targetLatitude, double targetLongitude, int page, int size) {
        String queryString = """
                select * from place_area ap
                where ap.status = :status and ap.region_id = :regionId
                order by (6371 * acos(cos(radians(:latitude)) * cos(radians(ap.latitude)) *
                cos(radians(ap.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(ap.latitude))))
                limit :size offset :pageSize
                """;
        Query nativeQuery = entityManager.createNativeQuery(queryString, PlaceAreaEntity.class);
        nativeQuery.setParameter("status", PlaceAreaStatus.AVAILABLE.name());
        nativeQuery.setParameter("regionId", regionId);
        nativeQuery.setParameter("latitude", targetLatitude);
        nativeQuery.setParameter("longitude", targetLongitude);
        nativeQuery.setParameter("size", size);
        nativeQuery.setParameter("pageSize", size * page);
        return nativeQuery.getResultList();
    }

    // Helper method to calculate distance between two points using Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula implementation to calculate distance
        // You can find implementations online or use libraries like GeoTools for more accurate calculations
        // For simplicity, I'll provide a basic implementation
        double radius = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radius * c;
    }
}
