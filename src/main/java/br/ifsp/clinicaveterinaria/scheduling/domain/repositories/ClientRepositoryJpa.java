package br.ifsp.clinicaveterinaria.scheduling.domain.repositories;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client;
import br.ifsp.clinicaveterinaria.scheduling.infra.mappers.ClientMapper;
import br.ifsp.clinicaveterinaria.scheduling.infra.persistence.Repository;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.ClientJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class ClientRepositoryJpa implements Repository<Client, String> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void add(Client aggregate) {
        String id = aggregate.getCpf().getCPF();
        if (em.find(ClientJpaEntity.class, id) != null) {
            throw new IllegalStateException("Client already exists: " + id);
        }
        em.persist(ClientMapper.toEntity(aggregate));
    }

    @Override
    @Transactional
    public void update(Client aggregate) {
        String id = aggregate.getCpf().getCPF();
        ClientJpaEntity existing = em.find(ClientJpaEntity.class, id);
        if (existing == null) {
            throw new IllegalStateException("Client not found: " + id);
        }
        em.merge(ClientMapper.toEntity(aggregate));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        ClientJpaEntity e = em.find(ClientJpaEntity.class, id);
        if (e != null) em.remove(e);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(String id) {
        ClientJpaEntity e = em.find(ClientJpaEntity.class, id);
        return Optional.ofNullable(e).map(ClientMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return em.find(ClientJpaEntity.class, id) != null;
    }
}
