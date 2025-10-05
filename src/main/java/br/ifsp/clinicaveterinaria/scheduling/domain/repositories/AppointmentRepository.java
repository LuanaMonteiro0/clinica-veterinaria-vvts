package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Appointment;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends Repository<Appointment, Long> {
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByVetAndDate(Veterinarian vet, LocalDate date);
    List<Appointment> findByVetAndDateBetween(Veterinarian vet, LocalDate start, LocalDate end);
}