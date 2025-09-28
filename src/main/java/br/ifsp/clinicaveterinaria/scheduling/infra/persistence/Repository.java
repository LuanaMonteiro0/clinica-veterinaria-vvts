package br.ifsp.clinicaveterinaria.scheduling.infra.persistence;

import java.util.Optional;

public interface Repository<T, ID> {
    void add(T aggregate);
    void update(T aggregate);
    void deleteById(ID id);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
}

