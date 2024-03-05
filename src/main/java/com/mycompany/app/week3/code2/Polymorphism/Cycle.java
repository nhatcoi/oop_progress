package com.mycompany.app.week3.code2.Polymorphism;

public class Cycle {
    public void ride() {
        System.out.println("Riding Cycle");
    }
}

class Unicycle extends Cycle {
    @Override
    public void ride() {
        System.out.println("Riding Unicycle");
    }

    public void balance() {
        System.out.println("Balancing Unicycle");
    }
}

class Bicycle extends Cycle {
    @Override
    public void ride() {
        System.out.println("Riding Bicycle");
    }

    public void balance() {
        System.out.println("Balancing Bicycle");
    }
}

class Tricycle extends Cycle {
    @Override
    public void ride() {
        System.out.println("Riding Tricycle");
    }
}