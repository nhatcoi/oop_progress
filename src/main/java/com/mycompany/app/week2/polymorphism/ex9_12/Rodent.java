package com.mycompany.app.week2.polymorphism.ex9_12;

public class Rodent {
    private String name;
    private Teeth teeth;

    public Rodent(String name, Teeth sharedTeeth) {
        this.name = name;
        this.teeth = new Teeth();
    }

    public void eat() {}
    public void run() {}
    public void sleep() {}
}
