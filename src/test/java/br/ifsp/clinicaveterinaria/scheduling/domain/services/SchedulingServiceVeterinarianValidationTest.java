package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class SchedulingServiceVeterinarianValidationTest {

    @Test
    void shouldThrowIllegalArgumentAndCancelAttemptWhenNoVeterinarianSelected() {
        Repository veterinarianRepo = mock(Repository.class);
        Repository appointmentRepo  = mock(Repository.class);

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo);

        CPF cpf = new CPF();
        cpf.setCPF("529.982.247-25");
        Client client = new Client("Fulano", new Phone("(11) 11111-1111"), cpf, List.of());

        assertThatThrownBy(() -> service.requestAppointment(client, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("veterin");

        verifyNoInteractions(appointmentRepo);
    }
}
