package com.worker_service.serviceImpl;

import com.worker_service.dto.RatingDTO;
import com.worker_service.service.RatingFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class RatingClientService {

    private final RatingFeign ratingFeign;

    /**
     * Services communicate using ""Rest templates""
     * this is not recommended
     * use for ony legacy project
     * It is use without adding any dependency, Much boilerplate code
     * <p>
     * Remote call → MUST be protected by Circuit Breaker
     * NO try-catch here ❌
     */
    @CircuitBreaker(
            name = "Rating-Service",
            fallbackMethod = "fallback"
    )
    public List<RatingDTO> getRatings(Long workerId) {
        return ratingFeign.getRatingsByReceiverId(workerId.toString()).getData();
    }

    /**
     * For Rest templates
     * public List<RatingDTO> getRatings(Long workerId) {
     * String url =
     * "http://RATING-SERVICE/ratingService/ratings/getRatingsByReceiverId/" + workerId;
     * <p>
     * ResponseEntity<ApiResponse<List<RatingDTO>>> response =
     * restTemplate.exchange(
     * url,
     * HttpMethod.GET,
     * null,
     * new ParameterizedTypeReference<>() {
     * }
     * );
     * }
     **/

    public List<RatingDTO> fallback(Long workerId, Throwable ex) {
        log.error("Rating service DOWN for workerId {}", workerId, ex);

        return Collections.emptyList();
    }
}

