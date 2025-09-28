package br.ifsp.clinicaveterinaria.scheduling.infra.mappers;

import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Animal;
import br.ifsp.clinicaveterinaria.scheduling.domain.entities.Client;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.AnimalJpaEntity;
import br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities.ClientJpaEntity;

import java.util.ArrayList;
import java.util.List;

public final class ClientMapper {

    private ClientMapper() {}

    public static ClientJpaEntity toEntity(Client c) {
        String cpf = c.getCpf().getCPF();
        String phone = c.getPhone().getPhone();

        ClientJpaEntity e = new ClientJpaEntity(cpf, c.getName(), phone);

        if (c.getAnimal() != null) {
            List<AnimalJpaEntity> animals = new ArrayList<>();
            for (Animal a : c.getAnimal()) {
                animals.add(new AnimalJpaEntity(
                        a.getName(),
                        a.getAge(),
                        a.getBreed(),
                        a.getWeight(),
                        e
                ));
            }
            e.setAnimals(animals);
        }
        return e;
    }

    public static Client toDomain(ClientJpaEntity e) {
        CPF cpf = new CPF();
        cpf.setCPF(e.getCpf());
        Phone phone = new Phone();
        phone.setPhone(e.getPhone()); 
        List<Animal> animals = new ArrayList<>();
        if (e.getAnimals() != null) {
            for (AnimalJpaEntity a : e.getAnimals()) {
                animals.add(new Animal(
                        a.getId(),
                        a.getName(),
                        a.getAge(),
                        a.getBreed(),
                        a.getWeight()
                ));
            }
        }

        return new Client(
                e.getName(),
                phone,
                cpf,
                animals
        );
    }
}


