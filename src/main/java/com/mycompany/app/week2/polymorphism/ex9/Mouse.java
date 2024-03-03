package com.mycompany.app.week2.polymorphism.ex9;

public class Mouse extends Rodent{

    @Override
    public void eat() {
        System.out.println("Mouse is gobble");
    }

    @Override
    public void run() {
        System.out.println("Mouse run fast");
    }

    @Override
    public void sleep() {
        System.out.println("Mouse is sleeping");
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
