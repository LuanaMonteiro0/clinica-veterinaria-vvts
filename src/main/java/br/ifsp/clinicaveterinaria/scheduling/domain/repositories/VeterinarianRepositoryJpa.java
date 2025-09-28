package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;


import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Veterinarian;
import br.ifsp.clinicaveterinaria.scheduling.infra.mappers.VeterinarianMapper;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.VeterinarianJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class VeterinarianRepositoryJpa implements Repository<Veterinarian, String> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void add(Veterinarian aggregate) {
        String id = aggregate.getCrmv().getCrmv();
        if (em.find(VeterinarianJpaEntity.class, id) != null) {
            throw new IllegalStateException("Veterinarian already exists: " + id);
        }
        em.persist(VeterinarianMapper.toEntity(aggregate));
    }

    @Override
    @Transactional
    public void update(Veterinarian aggregate) {
        String id = aggregate.getCrmv().getCrmv();
        VeterinarianJpaEntity existing = em.find(VeterinarianJpaEntity.class, id);
        if (existing == null) {
            throw new IllegalStateException("Veterinarian not found: " + id);
        }
        em.merge(VeterinarianMapper.toEntity(aggregate));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        VeterinarianJpaEntity e = em.find(VeterinarianJpaEntity.class, id);
        if (e != null) em.remove(e);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Veterinarian> findById(String id) {
        VeterinarianJpaEntity e = em.find(VeterinarianJpaEntity.class, id);
        return Optional.ofNullable(e).map(VeterinarianMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return em.find(VeterinarianJpaEntity.class, id) != null;
    }
}
