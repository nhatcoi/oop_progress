--- Test case polymorphism ---
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



test interface exercise 
--
package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.Ex.ReUse.Ex9;
import com.mycompany.app.week2.Ex.ReUse.Ex9.Stem;
import com.mycompany.app.week2.interfaces.*;
import com.mycompany.app.week2.interfaces.Ex3.*;
import com.mycompany.app.week2.interfaces.Ex4.*;
import com.mycompany.app.week2.interfaces.Ex11.*;
import com.mycompany.app.week2.interfaces.Ex12.*;
import com.mycompany.app.week2.interfaces.Ex14.*;
import com.mycompany.app.week2.interfaces.Ex18.*;
import com.mycompany.app.week2.interfaces.Ex18;
import com.mycompany.app.week2.interfaces.Ex19;
import com.mycompany.app.week2.interfaces.Ex19.*;

import java.util.Scanner;


public class App {
static Debug debug = new Debug();

    static void callMethod(Base4 b) {
        b.method();
    }

    public static void rideCycle(CycleFactory factory) {
        Cycle cycle = factory.createCycle();
        cycle.ride();
    }

    public static void performToss(TossFactory tossFactory) {
        Ex19.TossOutcome tossOutcome = tossFactory.createTossOutcome();
        tossOutcome.displayOutcome();
    }

    public class Musics {
        static void tune(Playable p) {
            p.play(new Note());
        }
    public static void main(String[] args) {
        System.out.println("Group 17");

        Stem stem = new Ex9().new Stem();

        // exercise 3
        Derived3 d = new Ex3().new Derived3();
        d.print();

        // exercise 4
        Derived4 derived4 = new Ex4().new Derived4();
        callMethod(derived4);

        // exercise 5
        Ex5 car = new Ex5();
        car.mirror();
        car.depot();
        car.wheels();

        // exercise 11
        String s = "abcdef";
        Apply.process(new Ex11.SwapPairs(), s);

        // exercise 12
        Hero hero = new Ex12.Hero();

        // Using interfaces through static methods in Adventure class
        Adventure.t(hero); // CanFight
        Adventure.u(hero); // CanSwim
        Adventure.v(hero); // CanFly
        Adventure.c(hero); // CanFly
        Adventure.w(hero); // ActionCharacter

        // exercise 14
        NewClass myObject = new Ex14.NewClass();

        // Call methods using interfaces
        ((Interface1) myObject).method1();
        ((Interface1) myObject).method2();

        ((Interface2) myObject).method3();
        ((Interface2) myObject).method4();

        ((Interface3) myObject).method5();
        ((Interface3) myObject).method6();

        ((CombinedInterface) myObject).newMethod();

        // Call method from the concrete class
        myObject.concreteMethod();


        // exercise 16 - Scanner
        Scanner scanner = new Scanner(new Ex16.CharSequenceReader("Nguyen Van Nhat"));
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }

        // exercise 17 -  // Accessing the field without creating an instance of the interface
        System.out.println(Ex17.INTERFACE_FIELD);

        // exercise 18
        rideCycle(new Ex18().new UnicycleFactory());
        rideCycle(new Ex18().new BicycleFactory());
        rideCycle(new Ex18().new TricycleFactory());

        // exercise 19 - coin toss and dice toss
        // Perform a coin toss
        performToss(new Ex19().new CoinTossFactory());
        // Perform a dice toss
        performToss(new Ex19().new DiceTossFactory());

        // ex10
            Wind flute = new Wind();
            Percussion drum = new Percussion();
            Stringed violin = new Stringed();

            tune(flute);
            tune(drum);
            tune(violin);
        }
    }
    }

}