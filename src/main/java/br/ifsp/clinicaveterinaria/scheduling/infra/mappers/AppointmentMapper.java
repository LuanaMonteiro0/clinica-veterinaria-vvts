package br.ifsp.clinicaveterinaria.scheduling.infra.mappers;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.*;
import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ScheduledTimeEmbeddable;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.AppointmentJpaEntity;

public final class AppointmentMapper {

    private AppointmentMapper() {}

    public static void fillEntityScalarFields(Appointment source, AppointmentJpaEntity target) {
        target.setDate(source.getScheduledDate().getScheduledDate());
        target.setTime(new ScheduledTimeEmbeddable(
                source.getScheduledTime().getStartTime(),
                source.getScheduledTime().getEndTime()
        ));
    }

    public static Appointment toDomain(AppointmentJpaEntity e) {
        Client client = ClientMapper.toDomain(e.getClient());
        Veterinarian vet = VeterinarianMapper.toDomain(e.getVet());
        ServiceRoom room = ServiceRoomMapper.toDomain(e.getServiceRoom());
        Animal animal = AnimalMapper.toDomain(e.getAnimal());

        ScheduledDate date = new ScheduledDate(e.getDate());
        ScheduledTime time = new ScheduledTime(e.getTime().getStartTime(), e.getTime().getEndTime());

        Appointment appt = new Appointment(client, vet, date, time, room, animal);
        appt.setId(e.getId());
        return appt;
    }
}
