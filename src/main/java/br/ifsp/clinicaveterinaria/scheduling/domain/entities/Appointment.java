package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

public class Appointment {

    private Client client;
    private Veterinarian vet;
    private ScheduledDate scheduledDate;
    private ScheduledTime scheduledTime;
    private ServiceRoom serviceRoom;

    public Appointment(ServiceRoom serviceRoom, ScheduledTime scheduledTime, ScheduledDate scheduledDate, Veterinarian vet, Client client) {
        this.serviceRoom = serviceRoom;
        this.scheduledTime = scheduledTime;
        this.scheduledDate = scheduledDate;
        this.vet = vet;
        this.client = client;
    }

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
}
