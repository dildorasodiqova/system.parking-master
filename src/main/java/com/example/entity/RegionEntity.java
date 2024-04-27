package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

/**
 * @author 'Mukhtarov Sarvarbek' on 22.04.2024
 * @project system.parking
 * @contact @sarvargo
 */
@Entity
@Table(name = "region")
@Getter
@Setter
@SQLRestriction("visible = true")
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Boolean visible = true;
}
