package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

public class Veterinarian {

    private String name;
    private String email;
    private CRMV crmv;
    private Phone phone;


    public Veterinarian(String name, String email, CRMV crmv, Phone phone) {
        this.name = name;
        this.email = email;
        this.crmv = crmv;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CRMV getCrmv() {
        return crmv;
    }

    public void setCrmv(CRMV crmv) {
        this.crmv = crmv;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

}
