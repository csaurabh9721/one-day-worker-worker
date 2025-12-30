package com.worker_service.service;

import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.RatingDTO;
import com.worker_service.dto.UsersDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "Rating-Service")
public interface RatingFeign {
    @GetMapping("/ratingService/ratings/getRatingsByReceiverId/{receiverId}")
    ApiResponse<List<RatingDTO>> getRatingsByReceiverId(@PathVariable String receiverId);
}
