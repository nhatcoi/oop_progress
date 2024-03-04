package com.mycompany.app.week2.code2.polymorphism.ex2_3_4;

public class Triangle extends Shapes {
    @Override
    public void draw() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
    @Override
    public void erase() {
        System.out.println("Erasing a Triangle");
    }

    @Override
    public void message() {
        System.out.println("This is a Triangle.");
    }
}
