package com.mycompany.app.week1.code2;

public class Interaction {
    public void whileLoop() {
        int i = 0;
        do {
            System.out.println(i);
            i++;
        }
        while (i < 5);
    }

    public void forLoop() {
        // Outer loop
        for (int i = 1; i <= 2; i++) {
            System.out.println("Outer: " + i); // Executes 2 times

            // Inner loop
            for (int j = 1; j <= 3; j++) {
                System.out.println(" Inner: " + j); // Executes 6 times (2 * 3)
            }
        }
    }

    public void eachLoop() {
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        for (String i : cars) {
            System.out.println(i);
        }
    }
}
