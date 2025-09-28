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

    @Column(name="client_cpf", nullable = false, length = 14)
    private String clientCpf;

    @Column(name="vet_crmv", nullable = false)
    private String vetCrmv;

    @Column(name="date", nullable = false)
    private LocalDate date;

    @Embedded
    private ScheduledTimeEmbeddable time;

    @Column(name="service_room_code", nullable = false, length = 3)
    private String serviceRoomCode;

    @Column(name="animal_name")
    private String animalName;

    protected AppointmentJpaEntity() {}

    public AppointmentJpaEntity(Long id,
                                String clientCpf,
                                String vetCrmv,
                                LocalDate date,
                                ScheduledTimeEmbeddable time,
                                String serviceRoomCode,
                                String animalName) {
        this.id = id;
        this.clientCpf = clientCpf;
        this.vetCrmv = vetCrmv;
        this.date = date;
        this.time = time;
        this.serviceRoomCode = serviceRoomCode;
        this.animalName = animalName;
    }

    public Long getId() { return id; }
    public String getClientCpf() { return clientCpf; }
    public String getVetCrmv() { return vetCrmv; }
    public LocalDate getDate() { return date; }
    public ScheduledTimeEmbeddable getTime() { return time; }
    public String getServiceRoomCode() { return serviceRoomCode; }
    public String getAnimalName() { return animalName; }

    public void setId(Long id) { this.id = id; }
    public void setClientCpf(String clientCpf) { this.clientCpf = clientCpf; }
    public void setVetCrmv(String vetCrmv) { this.vetCrmv = vetCrmv; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setTime(ScheduledTimeEmbeddable time) { this.time = time; }
    public void setServiceRoomCode(String serviceRoomCode) { this.serviceRoomCode = serviceRoomCode; }
    public void setAnimalName(String animalName) { this.animalName = animalName; }
}
