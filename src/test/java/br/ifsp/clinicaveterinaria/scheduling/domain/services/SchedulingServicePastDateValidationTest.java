package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ScheduledDate;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ServiceRoomRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.VeterinarianRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class SchedulingServicePastDateValidationTest {

    @Test
    void shouldThrowErrorWhenPastDateIsSelected() {
        VeterinarianRepository veterinarianRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo  = mock(AppointmentRepository.class);
        ServiceRoomRepository roomRepo = mock(ServiceRoomRepository.class);

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo, roomRepo);

        CPF cpf = new CPF();
        cpf.setCPF("529.982.247-25");
        Client client = new Client("John Doe", new Phone("(11) 98765-4321"), cpf, Collections.emptyList());
        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));

        LocalDate pastDate = LocalDate.now().minusDays(1);
        ScheduledDate scheduledPastDate = new ScheduledDate(pastDate);

        assertThatThrownBy(() -> service.requestAppointment(client, vet, scheduledPastDate, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Não é possível agendar em uma data passada.");

        verifyNoInteractions(appointmentRepo);
    }
}