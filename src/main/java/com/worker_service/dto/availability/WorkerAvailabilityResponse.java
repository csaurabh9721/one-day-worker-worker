package com.worker_service.dto.availability;

import com.worker_service.enums.DayOfWeekType;

import java.time.LocalTime;

public record WorkerAvailabilityResponse(

        Long id,

        DayOfWeekType dayOfWeek,

        LocalTime startTime,

        LocalTime endTime,

        Boolean active
) {
}