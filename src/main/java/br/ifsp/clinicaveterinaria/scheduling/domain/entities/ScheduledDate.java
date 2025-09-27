package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ScheduledDate {

    private LocalDate scheduledDate;

    public ScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        if (scheduledDate == null) throw new IllegalArgumentException("Data agendada não pode ser nula.");
        this.scheduledDate = scheduledDate;
    }
}
