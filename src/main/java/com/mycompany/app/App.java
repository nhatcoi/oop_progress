package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex9;
import com.mycompany.app.week2.Ex.ReUse.Ex9.Stem;
import com.mycompany.app.week2.polymorphism.ex10.Base;
import com.mycompany.app.week2.polymorphism.ex10.Derived;
import com.mycompany.app.week2.polymorphism.ex11.Detergent;
import com.mycompany.app.week2.polymorphism.ex1_5.Cycle;
import com.mycompany.app.week2.polymorphism.ex1_5.Bicycle;
import com.mycompany.app.week2.polymorphism.ex1_5.Tricycle;
import com.mycompany.app.week2.polymorphism.ex1_5.Unicycle;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Circle;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Shapes;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Square;
import com.mycompany.app.week2.polymorphism.ex2_3_4.Triangle;
import com.mycompany.app.week2.polymorphism.ex6_7_8.*;
import com.mycompany.app.week2.polymorphism.ex9.Gerbil;
import com.mycompany.app.week2.polymorphism.ex9.Hamster;
import com.mycompany.app.week2.polymorphism.ex9.Mouse;
import com.mycompany.app.week2.polymorphism.ex9.Rodent;

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

        // exercise 7 - verify that polymorphism works for your new type.
        Note note = new Note();
        Instrument instrument = new Instrument();
        instrument.play(note, 0);
        instrument = new Wind();
        instrument.play(note, 6);
        System.out.println();

        // exercise 8 - randomly creates Instrument objects
        Instrument[] orchestra = {
                new Wind(),
                new Percussion(),
                new Stringed(),
                new Brass(),
                new Woodwind()
        };
        Music3.tuneAll(orchestra);
        // exercise 6 - toString method
        Music3.toString(orchestra);


        // exercise 9 - Rodent
        Rodent[] rodents = {
            new Mouse(),
            new Gerbil(),
            new Hamster()
        };

        for (Rodent rodent : rodents) {
            rodent.eat();
            rodent.sleep();
            rodent.run();
        }

        // exercise 10 - Base - Derived
        Base base = new Derived();
        base.method1();
        // Explain : When method1 is called, it in turn calls method2, which is overridden in Derived. Because of polymorphism, the method2 of Derived is called, not method2 of Base

        // exercise 11 - Detergent - it uses delegation.
        Detergent detergent = new Detergent();
        detergent.append("Diana");
        detergent.scrub();
        detergent.apply();
        detergent.dilute();
        detergent.foam();
        System.out.println(detergent.toString());
    }
}