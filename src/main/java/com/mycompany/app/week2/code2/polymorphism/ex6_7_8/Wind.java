package com.mycompany.app.week2.code2.polymorphism.ex6_7_8;

public class Wind extends Instrument{
    public void play(Note n, int i) {
        System.out.println("Wind.play() - " + n.note[i]);
    }

    public String what() {
        return "Wind";
    }

    public void adjust() {
        System.out.println("Adjusting Wind");
    }

}
