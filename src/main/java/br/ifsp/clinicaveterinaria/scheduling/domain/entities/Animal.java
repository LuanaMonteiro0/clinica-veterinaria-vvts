package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

import br.ifsp.clinicaveterinaria.scheduling.domain.valueobjects.Phone;

public class Animal {
    private String name;
    private int Age;
    private String breed;
    private Phone phone;

    public Animal(String name, int age, String breed, Phone phone) {
        this.name = name;
        Age = age;
        this.breed = breed;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
