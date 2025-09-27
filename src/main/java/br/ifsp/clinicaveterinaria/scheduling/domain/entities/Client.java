package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.CPF;
import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;

import java.util.List;

public class Client {

    private String name;
    private Phone phone;
    private CPF cpf;
    private List<Animal> animal;

    public Client(String name, Phone phone, CPF cpf, List<Animal> animal) {
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.animal = animal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public List<Animal> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Animal> animal) {
        this.animal = animal;
    }

}
