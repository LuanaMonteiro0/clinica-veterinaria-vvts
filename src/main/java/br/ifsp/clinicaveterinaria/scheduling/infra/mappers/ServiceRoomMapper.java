package br.ifsp.clinicaveterinaria.scheduling.infra.mappers;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ServiceRoom;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.ServiceRoomJpaEntity;

public final class ServiceRoomMapper {

    private ServiceRoomMapper() {}

    public static ServiceRoomJpaEntity toEntity(ServiceRoom room) {
        return new ServiceRoomJpaEntity(room.getCode());
    }

    public static ServiceRoom toDomain(ServiceRoomJpaEntity entity) {
        return new ServiceRoom(entity.getCode());
    }

}