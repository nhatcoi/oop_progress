package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex9;
import com.mycompany.app.week2.Ex.ReUse.Ex9.Stem;
import com.mycompany.app.week2.polymorphism.Ex13;
import com.mycompany.app.week2.polymorphism.Ex15;
import com.mycompany.app.week2.polymorphism.Ex16;
import com.mycompany.app.week2.polymorphism.ex10.*;
import com.mycompany.app.week2.polymorphism.ex1_5_17.*;
import com.mycompany.app.week2.polymorphism.ex2_3_4.*;
import com.mycompany.app.week2.polymorphism.ex6_7_8.*;
import com.mycompany.app.week2.polymorphism.ex9_12_14.*;
import com.mycompany.app.week2.polymorphism.Ex11.*;
import com.mycompany.app.week2.polymorphism.Ex13.*;
import com.mycompany.app.week2.polymorphism.Ex16.*;

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


        // exercise 9, 12, 14 -
        Teeth sharedTeeth = new Teeth();

        Rodent[] rodents = {
                new Mouse("Mickey", sharedTeeth),
                new Gerbil("Gerry", sharedTeeth),
                new Hamster("Harry", sharedTeeth),
                new Rodent("Generic Rodent", sharedTeeth)
        };

        for (Rodent rodent : rodents) {
            rodent.eat();
            rodent.sleep();
            rodent.run();
        }

        // Release the shared object after using it
        sharedTeeth.release(); // = 4

        // exercise 10 - Base - Derived
        Base base = new Derived();
        base.method1();
        // Explain : When method1 is called, it in turn calls method2, which is overridden in Derived. Because of polymorphism, the method2 of Derived is called, not method2 of Base

        // exercise 11 - // Order of constructor calls.
        new Sandwich();

        // exercise 13 -  verify the termination condition

        Shared shared = new Ex13().new Shared();
        Composing[] composing = {new Composing(shared),
                new Composing(shared), new Composing(shared),
                new Composing(shared), new Composing(shared)};
        for (Composing c : composing) {
            c.dispose();
        }
        shared.finalize2();

        // exercise 15
        new Ex15().new RoundGlyph(5);
        new Ex15().new RectangularGlyph(2, 3);

        // exercise 16
        Starship starship = new Ex16().new Starship();
        starship.toGreenAlert();
        starship.performAlert();
        starship.toYellowAlert();
        starship.performAlert();
        starship.toRedAlert();
        starship.performAlert();

        // exercise 17
        Cycle[] cycles = {new Unicycle(), new Bicycle(), new Tricycle()};

        ((Unicycle)cycles[0]).balance();
        ((Bicycle)cycles[1]).balance();

    }

}