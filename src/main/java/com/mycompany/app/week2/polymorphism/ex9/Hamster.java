package com.mycompany.app.week2.polymorphism.ex9;

public class Hamster implements Rodent{
    @Override
    public void eat() {
        System.out.println("Hamster is gobble");
    }

    @Override
    public void run() {
        System.out.println("Hamster run slow");
    }

    @Override
    public void sleep() {
        System.out.println("Hamster is sleeping");
    }

    @Override
    public String name(String n) {
        return n;
    }

    @Override
    public int age(int months) {
        return months;
    }
}

