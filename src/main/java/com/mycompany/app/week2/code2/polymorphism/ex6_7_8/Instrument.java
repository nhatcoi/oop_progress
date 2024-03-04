package com.mycompany.app.week2.code2.polymorphism.ex6_7_8;

public class Instrument {
    public void play(Note n, int i) {
        System.out.println("Instrument.play() - " + n.note[i]);
    }

    public String what() {
        return "Instrument";
    }

    public void adjust() {
        System.out.println("Adjusting Instrument");
    }
}
