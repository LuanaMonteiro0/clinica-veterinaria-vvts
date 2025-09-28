package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import java.util.List;
import java.util.Objects;

public class Appointment {

    private Long id;
    private Client client;
    private Veterinarian vet;
    private ScheduledDate scheduledDate;
    private ScheduledTime scheduledTime;
    private ServiceRoom serviceRoom;
    private Animal animal;

    public Appointment(Client client, Veterinarian vet, ScheduledDate scheduledDate, ScheduledTime scheduledTime, ServiceRoom serviceRoom, Animal animal) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(vet);
        Objects.requireNonNull(scheduledDate);
        Objects.requireNonNull(scheduledTime);
        Objects.requireNonNull(serviceRoom);
        Objects.requireNonNull(animal);

        List<Animal> clientAnimals = Objects.requireNonNull(client.getAnimal());
        if (!clientAnimals.contains(animal)) {
            throw new IllegalArgumentException("O animal do Appointment deve pertencer ao Client.");
        }

        this.client = client;
        this.vet = vet;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
        this.serviceRoom = serviceRoom;
        this.animal = animal;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Veterinarian getVet() {
        return vet;
    }

    public void setVet(Veterinarian vet) {
        this.vet = vet;
    }

    public ScheduledDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(ScheduledDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public ScheduledTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(ScheduledTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public ServiceRoom getServiceRoom() {
        return serviceRoom;
    }

    public void setServiceRoom(ServiceRoom serviceRoom) {
        this.serviceRoom = serviceRoom;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

}
