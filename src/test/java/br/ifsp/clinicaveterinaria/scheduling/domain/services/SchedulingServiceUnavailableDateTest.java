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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SchedulingServiceUnavailableDateTest {

    @Test
    void shouldThrowErrorWhenUnavailableDayIsSelected() {
        VeterinarianRepository veterinarianRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo = mock(AppointmentRepository.class);
        ServiceRoomRepository roomRepo = mock(ServiceRoomRepository.class);

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo, roomRepo);

        CPF cpf = new CPF();
        cpf.setCPF("529.982.247-25");
        Client client = new Client("John Doe", new Phone("(11) 98765-4321"), cpf, Collections.emptyList());
        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));
        LocalDate unavailableDate = LocalDate.of(2025, 12, 25);
        ServiceRoom room = new ServiceRoom("101");
        Animal animal = new Animal("Rex", 5, "Vira-lata", 10.5);
        client.setAnimal(List.of(animal));

        List<Appointment> fullyBookedAppointments = new ArrayList<>();
        long numberOfSlots = 9;
        for (int i = 0; i < numberOfSlots; i++) {
            LocalTime slotStartTime = LocalTime.of(9, 0).plusHours(i);
            fullyBookedAppointments.add(new Appointment(client, vet, new ScheduledDate(unavailableDate), new ScheduledTime(slotStartTime, slotStartTime.plusHours(1)), room, animal));
        }

        when(appointmentRepo.findByVetAndDate(vet, unavailableDate)).thenReturn(fullyBookedAppointments);

        assertThatThrownBy(() -> service.requestAppointment(client, vet, new ScheduledDate(unavailableDate), null, animal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Data indisponível.");
    }
}