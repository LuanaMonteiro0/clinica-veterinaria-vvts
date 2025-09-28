package br.ifsp.clinicaveterinaria.scheduling.infra.mappers;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Animal;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.AnimalJpaEntity;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.ClientJpaEntity;

public final class AnimalMapper {

    private AnimalMapper() {}

    public static Animal toDomain(AnimalJpaEntity e) {
        if (e == null) return null;
        return new Animal(
                e.getId(),
                e.getName(),
                e.getAge(),
                e.getBreed(),
                e.getWeight()
        );
    }

    public static AnimalJpaEntity toEntity(Animal a, ClientJpaEntity owner) {
        if (a == null) return null;
        AnimalJpaEntity e = new AnimalJpaEntity(
                a.getName(),
                a.getAge(),
                a.getBreed(),
                a.getWeight(),
                owner
        );
        return e;
    }
}