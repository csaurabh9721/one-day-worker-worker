package com.worker_service.dto.availability;

import com.worker_service.enums.DayOfWeekType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record WorkerAvailabilityRequest(

        @NotNull
        DayOfWeekType dayOfWeek,

        @NotNull
        LocalTime startTime,

        @NotNull
        LocalTime endTime,

        Boolean active
) {
}