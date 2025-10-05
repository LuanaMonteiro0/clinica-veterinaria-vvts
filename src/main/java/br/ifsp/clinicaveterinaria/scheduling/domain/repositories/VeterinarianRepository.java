package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import java.util.List;

public interface VeterinarianRepository {
    List<Veterinarian> findAll();
}