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

public class SchedulingServiceUnavailableTimeTest {

    @Test
    void shouldThrowErrorWhenUnavailableTimeIsSelected() {
        VeterinarianRepository veterinarianRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo = mock(AppointmentRepository.class);
        ServiceRoomRepository roomRepo = mock(ServiceRoomRepository.class);

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo, roomRepo);

        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));
        CPF cpf = new CPF();
        cpf.setCPF("123.456.789-10");
        Client client = new Client("John Doe", new Phone("(11) 98765-4321"), cpf, Collections.emptyList());
        Animal animal = new Animal("Bolinha", 2, "Poodle", 5.5);
        client.setAnimal(List.of(animal));
        ServiceRoom room = new ServiceRoom("101");

        ScheduledDate date = new ScheduledDate(LocalDate.now().plusDays(1));
        ScheduledTime unavailableTime = new ScheduledTime(LocalTime.of(10, 0), LocalTime.of(11, 0));

        Appointment existingAppointment = new Appointment(client, vet, date, unavailableTime, room, animal);
        when(appointmentRepo.findByVetAndDate(vet, date.getScheduledDate())).thenReturn(List.of(existingAppointment));

        assertThatThrownBy(() -> service.requestAppointment(client, vet, date, unavailableTime, animal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Horário indisponível.");

        verify(appointmentRepo, never()).add(any(Appointment.class));
    }
}