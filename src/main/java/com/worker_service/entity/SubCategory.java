package com.worker_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "category_id")
    private Long categoryId;
}
