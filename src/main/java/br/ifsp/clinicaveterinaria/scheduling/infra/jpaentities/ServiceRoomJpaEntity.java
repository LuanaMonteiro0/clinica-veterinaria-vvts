package br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities;

import jakarta.persistence.*;

@Entity
@Table(name = "service_room")
public class ServiceRoomJpaEntity {

    @Id
    @Column(name = "code", length = 3, nullable = false, unique = true)
    private String code;

    protected ServiceRoomJpaEntity() { }

    public ServiceRoomJpaEntity(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}