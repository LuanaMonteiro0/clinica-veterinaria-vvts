package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.VeterinarianRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class SchedulingServiceTest {

    @Test
    void shouldListAllVeterinariansWhenNoAppointmentsExist() {
        Veterinarian joao = new Veterinarian("Dr. João", "joao@gmail.com", new CRMV("CRMV-SP 0123"), new Phone("(12) 12345-2345"));
        Veterinarian maria = new Veterinarian("Dra. Maria","maria@gmail.com", new CRMV("CRMV-SP 0223"), new Phone("(12) 22345-2345"));

        VeterinarianRepository vetRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo = mock(AppointmentRepository.class);

        when(vetRepo.findAll()).thenReturn(List.of(joao, maria));
        LocalDate date = LocalDate.of(2025, 10, 10);
        when(appointmentRepo.findByDate(date)).thenReturn(Collections.emptyList());

        SchedulingService service = new SchedulingService(vetRepo, appointmentRepo);
        ScheduledDate appointmentDate = new ScheduledDate(date);

        List<Veterinarian> obtained = service.findAvailableVeterinarians(appointmentDate);

        assertThat(obtained).containsExactlyInAnyOrder(joao, maria);
    }

    @Test
    void givenAppointmentRequested_whenNoClientSelected_thenThrowIllegalArgument() {
        SchedulingService service = new SchedulingService(null, null);

        assertThatThrownBy(() -> service.requestAppointment(null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cliente");
    }
}