package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ScheduledDate other = (ScheduledDate) obj;
        return Objects.equals(scheduledDate, other.scheduledDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduledDate);
    }

}
