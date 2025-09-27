package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;

public class Veterinarian {

    private String name;
    private String email;
    private CRMV crmv;
    private Phone phone;


    public Veterinarian(String name, String email, Phone phone) {
        this.name = name;
        this.email = email;
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
