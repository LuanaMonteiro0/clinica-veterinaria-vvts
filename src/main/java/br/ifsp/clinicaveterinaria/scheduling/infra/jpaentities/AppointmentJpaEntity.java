package br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities;

import br.ifsp.clinicaveterinaria.scheduling.domain.repositories.ScheduledTimeEmbeddable;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointment")
public class AppointmentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_cpf", referencedColumnName = "cpf", nullable = false)
    private ClientJpaEntity client;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_crmv", referencedColumnName = "crmv", nullable = false)
    private VeterinarianJpaEntity vet;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_room_code", referencedColumnName = "code", nullable = false)
    private ServiceRoomJpaEntity serviceRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private AnimalJpaEntity animal;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Embedded
    private ScheduledTimeEmbeddable time;

    public AppointmentJpaEntity() {}

    public AppointmentJpaEntity(Long id,
                                ClientJpaEntity client,
                                VeterinarianJpaEntity vet,
                                ServiceRoomJpaEntity serviceRoom,
                                AnimalJpaEntity animal,
                                LocalDate date,
                                ScheduledTimeEmbeddable time) {
        this.id = id;
        this.client = client;
        this.vet = vet;
        this.serviceRoom = serviceRoom;
        this.animal = animal;
        this.date = date;
        this.time = time;
    }

    public Long getId() { return id; }
    public ClientJpaEntity getClient() { return client; }
    public VeterinarianJpaEntity getVet() { return vet; }
    public ServiceRoomJpaEntity getServiceRoom() { return serviceRoom; }
    public AnimalJpaEntity getAnimal() { return animal; }
    public LocalDate getDate() { return date; }
    public ScheduledTimeEmbeddable getTime() { return time; }

    public void setId(Long id) { this.id = id; }
    public void setClient(ClientJpaEntity client) { this.client = client; }
    public void setVet(VeterinarianJpaEntity vet) { this.vet = vet; }
    public void setServiceRoom(ServiceRoomJpaEntity serviceRoom) { this.serviceRoom = serviceRoom; }
    public void setAnimal(AnimalJpaEntity animal) { this.animal = animal; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(ScheduledTimeEmbeddable time) { this.time = time; }
}

