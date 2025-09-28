package br.ifsp.clinicaveterinaria.scheduling.infra.mappers;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.*;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ScheduledTimeEmbeddable;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.AppointmentJpaEntity;

import java.util.List;

public final class AppointmentMapper {

    private AppointmentMapper() {}

    public static AppointmentJpaEntity toEntity(Appointment a) {
        return new AppointmentJpaEntity(
                a.getId(),
                a.getClient().getCpf().getCPF(),
                a.getVet().getCrmv().getCrmv(),
                a.getScheduledDate().getScheduledDate(),
                new ScheduledTimeEmbeddable(
                        a.getScheduledTime().getStartTime(),
                        a.getScheduledTime().getEndTime()
                ),
                a.getServiceRoom().getCode(),
                a.getAnimal() != null ? a.getAnimal().getName() : null
        );
    }

    public static Appointment toDomain(AppointmentJpaEntity e) {
        CPF cpf = new CPF(); cpf.setCPF(e.getClientCpf());
        Phone phone = null;
        Client client = new Client(null, phone, cpf, List.of());

        CRMV crmv = new CRMV(); crmv.setCrmv(e.getVetCrmv());
        Veterinarian vet = new Veterinarian(null, null, crmv, null);

        ServiceRoom room = new ServiceRoom(e.getServiceRoomCode());

        ScheduledDate date = new ScheduledDate(e.getDate());
        ScheduledTime time = new ScheduledTime(e.getTime().getStartTime(), e.getTime().getEndTime());

        Animal animal = null;
        if (e.getAnimalName() != null) {



            animal = new Animal(e.getAnimalName(), 0, null, 0.0);
        }

        Appointment a = new Appointment(client, vet, date, time, room, animal);
        a.setId(e.getId());
        return a;
    }
}
