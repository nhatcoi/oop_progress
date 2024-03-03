package com.mycompany.app.week2.polymorphism.test;

// Modify Detergent.java so that it uses delegation.
public class Detergent {
    private String name;

    private Cleanser cleanser = new Cleanser();

    public Detergent(){}
    public Detergent(String name) {
        this.name = name;
    }

    public void append(String a) {
        cleanser.append(a);
    }
    public void dilute() {
        cleanser.dilute();
    }
    public void apply() {
        cleanser.apply();
    }
    public void scrub() {
        cleanser.scrub();
    }
    // new method to Detergent
    public void foam() {
        cleanser.append(" foam");
    }
    public String toString() {
        return cleanser.toString();
    }
}
