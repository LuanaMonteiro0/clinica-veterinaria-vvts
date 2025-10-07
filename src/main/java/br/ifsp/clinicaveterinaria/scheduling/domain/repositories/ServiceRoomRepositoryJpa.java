package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.ServiceRoom;
import br.ifsp.clinicaveterinaria.scheduling.infra.mappers.ServiceRoomMapper;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.ServiceRoomJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class ServiceRoomRepositoryJpa implements ServiceRoomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void add(ServiceRoom aggregate) {
        String id = aggregate.getCode();
        if (em.find(ServiceRoomJpaEntity.class, id) != null) {
            throw new IllegalStateException("ServiceRoom already exists: " + id);
        }
        em.persist(ServiceRoomMapper.toEntity(aggregate));
    }

    @Override
    @Transactional
    public void update(ServiceRoom aggregate) {
        String id = aggregate.getCode();
        ServiceRoomJpaEntity existing = em.find(ServiceRoomJpaEntity.class, id);
        if (existing == null) {
            throw new IllegalStateException("ServiceRoom not found: " + id);
        }
        em.merge(ServiceRoomMapper.toEntity(aggregate));
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<ServiceRoom> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Transactional
    public void deleteById(String id) {
        ServiceRoomJpaEntity e = em.find(ServiceRoomJpaEntity.class, id);
        if (e != null) em.remove(e);
    }


    @Transactional(readOnly = true)
    public Optional<ServiceRoom> findById(String id) {
        ServiceRoomJpaEntity e = em.find(ServiceRoomJpaEntity.class, id);
        return Optional.ofNullable(e).map(ServiceRoomMapper::toDomain);
    }

    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return em.find(ServiceRoomJpaEntity.class, id) != null;
    }

    @Override
    public Optional<ServiceRoom> findAvailable() {
        return Optional.empty();
    }
}
