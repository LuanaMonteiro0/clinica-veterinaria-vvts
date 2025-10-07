package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ServiceRoom;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import java.util.Optional;

public interface ServiceRoomRepository extends Repository<ServiceRoom, Long> {
    Optional<ServiceRoom> findAvailable();
}