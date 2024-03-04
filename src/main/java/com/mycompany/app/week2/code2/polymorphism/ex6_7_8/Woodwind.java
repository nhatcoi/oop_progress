package com.mycompany.app.week2.code2.polymorphism.ex6_7_8;

public class Woodwind extends Wind{
    public void play(Note n, int i) {
        System.out.println("Woodwind.play() - " + n.note[i]);
    }

    public String what() {
        return "Woodwind";
    }
}
