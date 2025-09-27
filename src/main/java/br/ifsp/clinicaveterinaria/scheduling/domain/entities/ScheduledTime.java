package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import java.time.LocalTime;

public class ScheduledTime {

    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduledTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null.");
        }

        if (this.endTime != null && !startTime.isBefore(this.endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be null.");
        }

        if (this.startTime != null && !endTime.isAfter(this.startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        this.endTime = endTime;
    }

    public void validateInterval() {
        if (startTime == null || endTime == null) {
            throw new IllegalStateException("Both start and end times must be set.");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalStateException("Start time must be before end time.");
        }
    }
}

