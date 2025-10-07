package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.*;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ServiceRoomRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SchedulingServiceAvailableDaysTest {

    @Test
    void shouldReturnDaysWithoutAppointmentsInRange() {
        CRMV crmv = new CRMV("CRMV/SP 0223");
        Veterinarian vet = new Veterinarian("Dra. Maria", "maria@gmail.com", crmv, new Phone("(12) 22345-2345"));
        Client client = new Client("Cliente Teste", new Phone("(11) 99999-9999"), null, Collections.emptyList());
        ServiceRoom room = new ServiceRoom("101");
        Animal animal = new Animal("Rex", 5, "Vira-lata", 10.5);
        client.setAnimal(List.of(animal));

        Appointment appointment1 = new Appointment(client, vet, new ScheduledDate(LocalDate.of(2025, 10, 10)), new ScheduledTime(LocalTime.of(10,0), LocalTime.of(11,0)), room, animal);
        Appointment appointment2 = new Appointment(client, vet, new ScheduledDate(LocalDate.of(2025, 10, 12)), new ScheduledTime(LocalTime.of(10,0), LocalTime.of(11,0)), room, animal);

        AppointmentRepository appointmentRepo = mock(AppointmentRepository.class);
        ServiceRoomRepository roomRepo = mock(ServiceRoomRepository.class);
        LocalDate start = LocalDate.of(2025, 10, 10);
        LocalDate end = LocalDate.of(2025, 10, 13);
        when(appointmentRepo.findByVetAndDateBetween(vet, start, end)).thenReturn(List.of(appointment1, appointment2));

        SchedulingService service = new SchedulingService(null, appointmentRepo, roomRepo);

        List<ScheduledDate> obtained = service.findAvailableDaysFor(vet, start, end);

        assertThat(obtained)
                .containsExactlyInAnyOrder(
                        new ScheduledDate(LocalDate.of(2025, 10, 11)),
                        new ScheduledDate(LocalDate.of(2025, 10, 13))
                );
    }
}