package com.worker_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerDetailsDto {
    private Long id;
    private String name;
    private Integer age;
    private String photo;
    private String mobile;
    private AddressDTO address;
    private List<RatingDTO> ratingList;
}
