package com.worker_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    private String id;
    private Long giverId;
    private Long receiverId;
    private int score;
    private String comment;
    private String createdAt;
    private String updatedAt;

}
