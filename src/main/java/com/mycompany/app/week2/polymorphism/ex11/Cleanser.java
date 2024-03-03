package com.mycompany.app.week2.polymorphism.ex11;

public class Cleanser {
    private String name = "Cleanser ";
    public void append(String a) {
        name += a;
    }

    public void dilute() {
        append(" dilute");
    }

    public void apply() {
        append(" apply");
    }

    public void scrub() {
        append(" scrub");
    }

    public String toString() {
        return name;
    }
}
