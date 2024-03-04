package com.mycompany.app.week2.code2.polymorphism.test;

public class SpaceShip extends SpaceShipControls{
    private String name;

    public SpaceShip(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
