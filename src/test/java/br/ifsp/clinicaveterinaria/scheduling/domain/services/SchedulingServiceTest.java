package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.InMemoryAppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.InMemoryVeterinarianRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SchedulingServiceTest {
    @Test
    void shouldListAllVeterinariansWhenNoAppointmentsExist() {

        Veterinarian joao = new Veterinarian("Dr. João", "joao@gmail.com", new CRMV("CRMV-SP 0123"), new Phone("(12) 12345-2345"));
        Veterinarian maria = new Veterinarian("Dra. Maria","maria@gmail.com", new CRMV("CRMV-SP 0223"), new Phone("(12) 22345-2345"));

        Repository vetRepo = new InMemoryVeterinarianRepository(
                List.of(
                        joao,
                        maria
                )
        );

        Repository appointmentRepo = new InMemoryAppointmentRepository();

        SchedulingService service = new SchedulingService(vetRepo, appointmentRepo);

        LocalDate date = LocalDate.of(2025, 10, 10);;

        ScheduledDate appointmentDate = new ScheduledDate(date);

        List<Veterinarian> obtained = service.findAvailableVeterinarians(appointmentDate);

        assertThat(obtained).containsExactlyInAnyOrder(joao, maria);
    }
}