package br.ifsp.clinicaveterinaria.scheduling.domain.entities;

public class Animal {
    private Long id;
    private String name;
    private int Age;
    private String breed;
    private double weight;

    public Animal() {

    }

    public Animal(String name, int age, String breed, double weight) {
        this.name = name;
        Age = age;
        this.breed = breed;
        this.weight = weight;
    }

    public Animal(Long id, String name, int age, String breed, double weight) {
        this.id = id;
        this.name = name;
        Age = age;
        this.breed = breed;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

}
