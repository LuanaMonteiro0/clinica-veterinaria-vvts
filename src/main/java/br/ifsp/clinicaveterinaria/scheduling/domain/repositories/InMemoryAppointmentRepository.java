package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Appointment;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryAppointmentRepository implements Repository {

    private final List<Appointment> appointments;

    public InMemoryAppointmentRepository() {
        this.appointments = new ArrayList<>();
    }

    public InMemoryAppointmentRepository(List<Appointment> appointments) {
        this.appointments = new ArrayList<>(appointments);
    }

    public List<Appointment> findAppointmentsByVeterinarianAndDate(Veterinarian veterinarian, ScheduledDate date) {
        return appointments.stream()
                .filter(a -> a.getVet().equals(veterinarian) && a.getScheduledDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Object aggregate) {

    }

    @Override
    public void update(Object aggregate) {

    }

    @Override
    public void deleteById(Object o) {

    }

    @Override
    public Optional findById(Object o) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Object o) {
        return false;
    }
}
