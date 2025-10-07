package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.*;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ServiceRoomRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.VeterinarianRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class SchedulingServicePastTimeValidationTest {

    @Test
    void shouldThrowErrorWhenPastTimeIsSelectedForToday() {
        VeterinarianRepository veterinarianRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo  = mock(AppointmentRepository.class);
        ServiceRoomRepository roomRepo = mock(ServiceRoomRepository.class);

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo, roomRepo);

        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));
        CPF cpf = new CPF();
        cpf.setCPF("529.982.247-25");
        Client client = new Client("John Doe", new Phone("(11) 98765-4321"), cpf, Collections.emptyList());
        Animal animal = new Animal("Bolinha", 2, "Poodle", 5.5);
        client.setAnimal(List.of(animal));

        ScheduledDate today = new ScheduledDate(LocalDate.now());
        LocalTime pastTime = LocalTime.now().minusHours(1);
        ScheduledTime scheduledPastTime = new ScheduledTime(pastTime, pastTime.plusHours(1));

        when(appointmentRepo.findByVetAndDate(vet, today.getScheduledDate())).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> service.requestAppointment(client, vet, today, scheduledPastTime, animal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Não é possível agendar em um horário passado.");

        verify(appointmentRepo, never()).add(any(Appointment.class));
    }
}