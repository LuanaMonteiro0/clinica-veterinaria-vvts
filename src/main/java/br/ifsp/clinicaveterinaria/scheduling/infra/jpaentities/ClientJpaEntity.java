package br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class ClientJpaEntity {

    @Id
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalJpaEntity> animals = new ArrayList<>();

    protected ClientJpaEntity() { }

    public ClientJpaEntity(String cpf, String name, String phone) {
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<AnimalJpaEntity> getAnimals() { return animals; }
    public void setAnimals(List<AnimalJpaEntity> animals) { this.animals = animals; }
}
