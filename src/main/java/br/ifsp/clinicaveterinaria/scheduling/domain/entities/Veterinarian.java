package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CRMV;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Veterinarian that = (Veterinarian) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(crmv, that.crmv) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, crmv, phone);
    }

}
