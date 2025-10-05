package br.ifsp.clinicaveterinaria.scheduling.domain.services;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class SchedulingServiceDateValidationTest {

    @Test
    void shouldThrowErrorAndCancelWhenNoDateIsSelected() {
        Repository veterinarianRepo = mock(Repository.class);
        Repository appointmentRepo  = mock(Repository.class);

        SchedulingService service = new SchedulingService(veterinarianRepo, appointmentRepo);

        CPF cpf = new CPF();
        cpf.setCPF("123.456.789-10");
        Client client = new Client("John Doe", new Phone("(11) 98765-4321"), cpf, Collections.emptyList());
        CRMV crmv = new CRMV();
        crmv.setCrmv("CRMV/SP 123456");
        Veterinarian vet = new Veterinarian("Dr. House", "house@vet.com", crmv, new Phone("(11) 12345-6789"));

        assertThatThrownBy(() -> service.requestAppointment(client, vet, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("data deve ser selecionada");

        verifyNoInteractions(appointmentRepo);
    }
}