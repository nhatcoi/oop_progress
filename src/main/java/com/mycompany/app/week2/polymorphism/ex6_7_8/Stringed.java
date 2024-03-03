package com.mycompany.app.week2.polymorphism.ex6_7_8;

public class Stringed extends Instrument{
    public void play(Note n, int i) {
        System.out.println("Stringed.play() - " + n.note[i]);
    }

    public String what() {
        return "Stringed";
    }

    public void adjust() {
        System.out.println("Adjusting Stringed");
    }
}
