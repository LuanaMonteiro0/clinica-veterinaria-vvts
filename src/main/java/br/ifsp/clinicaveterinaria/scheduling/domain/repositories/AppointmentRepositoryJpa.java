package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Appointment;
import br.ifsp.clinicaveterinaria.scheduling.infra.mappers.AppointmentMapper;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class AppointmentRepositoryJpa implements Repository<Appointment, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void add(Appointment aggregate) {
        var entity = new AppointmentJpaEntity();

        var clientRef = em.getReference(ClientJpaEntity.class, aggregate.getClient().getCpf().getCPF());
        var vetRef    = em.getReference(VeterinarianJpaEntity.class, aggregate.getVet().getCrmv().getCrmv());
        var roomRef   = em.getReference(ServiceRoomJpaEntity.class, aggregate.getServiceRoom().getCode());

        entity.setClient(clientRef);
        entity.setVet(vetRef);
        entity.setServiceRoom(roomRef);

        if (aggregate.getAnimal() != null && aggregate.getAnimal().getId() != null) {
            var animalRef = em.getReference(AnimalJpaEntity.class, aggregate.getAnimal().getId());
            entity.setAnimal(animalRef);
        } else {
            entity.setAnimal(null);
        }

        AppointmentMapper.fillEntityScalarFields(aggregate, entity);

        em.persist(entity);
        aggregate.setId(entity.getId());
    }

    @Override
    @Transactional
    public void update(Appointment aggregate) {
        if (aggregate.getId() == null) {
            throw new IllegalStateException("Appointment id is required for update.");
        }
        var existing = em.find(AppointmentJpaEntity.class, aggregate.getId());
        if (existing == null) {
            throw new IllegalStateException("Appointment not found: " + aggregate.getId());
        }

        var clientRef = em.getReference(ClientJpaEntity.class, aggregate.getClient().getCpf().getCPF());
        var vetRef    = em.getReference(VeterinarianJpaEntity.class, aggregate.getVet().getCrmv().getCrmv());
        var roomRef   = em.getReference(ServiceRoomJpaEntity.class, aggregate.getServiceRoom().getCode());

        existing.setClient(clientRef);
        existing.setVet(vetRef);
        existing.setServiceRoom(roomRef);

        if (aggregate.getAnimal() != null && aggregate.getAnimal().getId() != null) {
            var animalRef = em.getReference(AnimalJpaEntity.class, aggregate.getAnimal().getId());
            existing.setAnimal(animalRef);
        } else {
            existing.setAnimal(null);
        }

        AppointmentMapper.fillEntityScalarFields(aggregate, existing);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var e = em.find(AppointmentJpaEntity.class, id);
        if (e != null) em.remove(e);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Appointment> findById(Long id) {
        var e = em.createQuery("""
            SELECT a
            FROM AppointmentJpaEntity a
            JOIN FETCH a.client
            JOIN FETCH a.vet
            JOIN FETCH a.serviceRoom
            LEFT JOIN FETCH a.animal
            WHERE a.id = :id
        """, AppointmentJpaEntity.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElse(null);

        return Optional.ofNullable(e).map(AppointmentMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return em.find(AppointmentJpaEntity.class, id) != null;
    }

    @Transactional(readOnly = true)
    public List<Appointment> findAllFetched() {
        return em.createQuery("""
            SELECT a
            FROM AppointmentJpaEntity a
            JOIN FETCH a.client
            JOIN FETCH a.vet
            JOIN FETCH a.serviceRoom
            LEFT JOIN FETCH a.animal
        """, AppointmentJpaEntity.class)
                .getResultList()
                .stream()
                .map(AppointmentMapper::toDomain)
                .toList();
    }
}
