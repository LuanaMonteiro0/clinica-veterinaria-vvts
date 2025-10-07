package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import java.util.List;

public interface VeterinarianRepository extends Repository<Veterinarian, Long> {
    List<Veterinarian> findAll();
}