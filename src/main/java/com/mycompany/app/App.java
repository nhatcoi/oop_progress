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
    }

}