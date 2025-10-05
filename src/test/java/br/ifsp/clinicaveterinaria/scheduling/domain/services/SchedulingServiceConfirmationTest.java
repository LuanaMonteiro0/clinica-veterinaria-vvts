package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.*;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ServiceRoomRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.VeterinarianRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class SchedulingServiceConfirmationTest {

    @Test
    void shouldConfirmAppointmentAndAssignRoomWhenTimeIsSelected() {
        VeterinarianRepository veterinarianRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo = mock(AppointmentRepository.class);
        ServiceRoomRepository roomRepo = mock(ServiceRoomRepository.class);

        ServiceRoom expectedRoom = new ServiceRoom("101");
        when(roomRepo.findAvailable()).thenReturn(Optional.of(expectedRoom));

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo, roomRepo);

        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));
        CPF cpf = new CPF();
        cpf.setCPF("529.982.247-25");
        Client client = new Client("John Doe", new Phone("(11) 98765-4321"), cpf, Collections.emptyList());
        Animal animal = new Animal("Bolinha", 2, "Poodle", 5.5);
        client.setAnimal(List.of(animal));

        ScheduledDate date = new ScheduledDate(LocalDate.now().plusDays(1));
        ScheduledTime time = new ScheduledTime(LocalTime.of(10, 0), LocalTime.of(11, 0));

        Appointment confirmedAppointment = service.requestAppointment(client, vet, date, time, animal);

        assertThat(confirmedAppointment).isNotNull();
        assertThat(confirmedAppointment.getServiceRoom()).isEqualTo(expectedRoom);
        assertThat(confirmedAppointment.getVet()).isEqualTo(vet);
        assertThat(confirmedAppointment.getClient()).isEqualTo(client);
        assertThat(confirmedAppointment.getAnimal()).isEqualTo(animal);

        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepo).add(appointmentCaptor.capture());
        Appointment savedAppointment = appointmentCaptor.getValue();
        assertThat(savedAppointment.getServiceRoom()).isEqualTo(expectedRoom);
    }

    @Test
    void shouldThrowErrorWhenNoRoomIsAvailable() {
        VeterinarianRepository veterinarianRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo = mock(AppointmentRepository.class);
        ServiceRoomRepository roomRepo = mock(ServiceRoomRepository.class);

        when(roomRepo.findAvailable()).thenReturn(Optional.empty());

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo, roomRepo);

        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));
        CPF cpf = new CPF();
        cpf.setCPF("529.982.247-25");
        Client client = new Client("John Doe", new Phone("(11) 98765-4321"), cpf, Collections.emptyList());
        Animal animal = new Animal("Bolinha", 2, "Poodle", 5.5);
        client.setAnimal(List.of(animal));

        ScheduledDate date = new ScheduledDate(LocalDate.now().plusDays(1));
        ScheduledTime time = new ScheduledTime(LocalTime.of(10, 0), LocalTime.of(11, 0));

        assertThatThrownBy(() -> service.requestAppointment(client, vet, date, time, animal))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Nenhuma sala de atendimento disponível.");

        verify(appointmentRepo, never()).add(any());
    }
}