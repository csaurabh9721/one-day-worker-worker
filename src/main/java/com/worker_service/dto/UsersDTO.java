package com.worker_service.dto;

import lombok.Data;

@Data
public class UsersDTO {
    private Long id;
    private String name;
    private Integer age;
    private String photo;
    private String mobile;
}
