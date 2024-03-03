package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex9;
import com.mycompany.app.week2.Ex.ReUse.Ex9.Stem;
import com.mycompany.app.week2.polymorphism.ex1_5.Cycle;
import com.mycompany.app.week2.polymorphism.ex1_5.Bicycle;
import com.mycompany.app.week2.polymorphism.ex1_5.Tricycle;
import com.mycompany.app.week2.polymorphism.ex1_5.Unicycle;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Circle;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Shapes;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Square;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Triangle;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Stem stem = new Ex9().new Stem();

        // exercise 1, 5
        // example test for polymorphism upcast to Cycle via a ride() method.
        Cycle cycle = new Cycle();
        cycle.ride();
        cycle = new Unicycle();
        cycle.ride();
        cycle = new Bicycle();
        cycle.ride();
        cycle = new Tricycle();
        cycle.ride();

        Cycle cycle1 = new Unicycle();
        int wheelsCycle1 = 1;
        System.out.println("Number of wheels Unicycle: " + cycle1.wheels(wheelsCycle1));

        Cycle cycle2 = new Bicycle();
        int wheelsCycle2 = 2;
        System.out.println("Number of wheels Bicycle: " + cycle2.wheels(wheelsCycle2));

        Cycle cycle3 = new Tricycle();
        int wheelsCycle3 = 3;
        System.out.println("Number of wheels Tricycle: " + cycle3.wheels(wheelsCycle3));

        // exercise 3, 4, 5
        // example test for polymorphism add the @Override annotation to the shapes example.
        Shapes shape1 = new Square();
        shape1.draw();
        shape1 = new Triangle();
        shape1.draw();
        Shapes shape2 = new Circle();
        shape2.message();


    }
}