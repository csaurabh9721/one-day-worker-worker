package com.worker_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String streetName;
    private String locality;
    private String sublocality;
    private String city;
    private String state;
    private String country;
    private Integer pinCode;

    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private Worker worker;
}
