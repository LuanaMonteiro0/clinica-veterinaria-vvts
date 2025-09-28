package br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities;

import jakarta.persistence.*;

@Entity
@Table(name = "animal")
public class AnimalJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="age", nullable = false)
    private int age;

    @Column(name="breed", nullable = false)
    private String breed;

    @Column(name="weight", nullable = false)
    private double weight;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_cpf", nullable = false)
    private ClientJpaEntity owner;

    protected AnimalJpaEntity() { }

    public AnimalJpaEntity(String name, int age, String breed, double weight, ClientJpaEntity owner) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.weight = weight;
        this.owner = owner;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getBreed() { return breed; }
    public double getWeight() { return weight; }
    public ClientJpaEntity getOwner() { return owner; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setOwner(ClientJpaEntity owner) { this.owner = owner; }
}
