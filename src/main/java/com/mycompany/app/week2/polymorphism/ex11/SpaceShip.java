package com.mycompany.app.week2.polymorphism.ex11;

public class SpaceShip extends SpaceShipControls{
    private String name;

    public SpaceShip(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
}
