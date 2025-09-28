package br.ifsp.clinicaveterinaria.scheduling.infra.jpaentities;


import jakarta.persistence.*;

@Entity
@Table(name = "veterinarian")
public class VeterinarianJpaEntity {

    @Id
    @Column(name = "crmv", nullable = false, unique = true)
    private String crmv;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    protected VeterinarianJpaEntity() { }

    public VeterinarianJpaEntity(String crmv, String name, String email, String phone) {
        this.crmv = crmv;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getCrmv() { return crmv; }
    public void setCrmv(String crmv) { this.crmv = crmv; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}