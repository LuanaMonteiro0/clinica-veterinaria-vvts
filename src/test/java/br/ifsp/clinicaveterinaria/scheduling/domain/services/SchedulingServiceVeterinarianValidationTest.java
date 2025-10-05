package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.AppointmentRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.VeterinarianRepository;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class SchedulingServiceVeterinarianValidationTest {

    @Test
    void shouldThrowIllegalArgumentAndCancelAttemptWhenNoVeterinarianSelected() {
        VeterinarianRepository veterinarianRepo = mock(VeterinarianRepository.class);
        AppointmentRepository appointmentRepo  = mock(AppointmentRepository.class);

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo);

        CPF cpf = new CPF();
        cpf.setCPF("529.982.247-25");
        Client client = new Client("Fulano", new Phone("(11) 11111-1111"), cpf, List.of());

        assertThatThrownBy(() -> service.requestAppointment(client, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("veterinário");

        verifyNoInteractions(appointmentRepo);
    }
}