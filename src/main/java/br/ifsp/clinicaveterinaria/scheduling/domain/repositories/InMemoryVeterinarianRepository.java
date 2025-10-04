package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryVeterinarianRepository implements Repository {
    private final List<Veterinarian> veterinarians;

    public InMemoryVeterinarianRepository(List<Veterinarian> veterinarians) {
        this.veterinarians = veterinarians;
    }

    public List<Veterinarian> findAll() {
        return new ArrayList<>(veterinarians);
    }

    @Override
    public void add(Object aggregate) {

    }

    @Override
    public void update(Object aggregate) {

    }

    @Override
    public void deleteById(Object o) {

    }

    @Override
    public Optional findById(Object o) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Object o) {
        return false;
    }
}
