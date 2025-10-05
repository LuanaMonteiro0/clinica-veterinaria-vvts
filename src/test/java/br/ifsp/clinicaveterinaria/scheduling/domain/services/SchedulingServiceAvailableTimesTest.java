package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.*;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
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

public class SchedulingServiceAvailableTimesTest {

    @Test
    void shouldListAvailableTimesForSelectedDay() {
        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));
        LocalDate selectedDate = LocalDate.of(2025, 10, 20);

        Client client = new Client("Cliente Teste", new Phone("(11) 98765-4321"), null, Collections.emptyList());
        Animal animal = new Animal("Bolinha", 2, "Poodle", 5.5);
        client.setAnimal(List.of(animal));
        ServiceRoom room = new ServiceRoom("001");

        Appointment bookedAppointment = new Appointment(
                client,
                vet,
                new ScheduledDate(selectedDate),
                new ScheduledTime(LocalTime.of(10, 0), LocalTime.of(11, 0)),
                room,
                animal
        );

        AppointmentRepository appointmentRepo = mock(AppointmentRepository.class);

        when(appointmentRepo.findByVetAndDate(vet, selectedDate)).thenReturn(List.of(bookedAppointment));

        SchedulingService service = new SchedulingService(null, appointmentRepo);

        List<ScheduledTime> availableTimes = service.findAvailableTimesFor(vet, new ScheduledDate(selectedDate));

        assertThat(availableTimes).hasSize(8);
        assertThat(availableTimes).doesNotContain(new ScheduledTime(LocalTime.of(10, 0), LocalTime.of(11, 0)));
        assertThat(availableTimes).containsExactly(
                new ScheduledTime(LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new ScheduledTime(LocalTime.of(11, 0), LocalTime.of(12, 0)),
                new ScheduledTime(LocalTime.of(12, 0), LocalTime.of(13, 0)),
                new ScheduledTime(LocalTime.of(13, 0), LocalTime.of(14, 0)),
                new ScheduledTime(LocalTime.of(14, 0), LocalTime.of(15, 0)),
                new ScheduledTime(LocalTime.of(15, 0), LocalTime.of(16, 0)),
                new ScheduledTime(LocalTime.of(16, 0), LocalTime.of(17, 0)),
                new ScheduledTime(LocalTime.of(17, 0), LocalTime.of(18, 0))
        );
    }
}