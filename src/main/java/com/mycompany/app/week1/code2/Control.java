package com.mycompany.app.week1.code2;

public class Control {
    int time = 22;
    public void control() {
        if (time < 10) {
            System.out.println("Good morning.");
        } else if (time < 20) {
            System.out.println("Good day.");
        } else {
            System.out.println("Good evening.");
        }
    }
}
