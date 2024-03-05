package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.code2.interfaces.Ex3;
import com.mycompany.app.week2.code2.interfaces.Ex3.*;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Bicycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Cycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Tricycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Unicycle;
import com.mycompany.app.week2.code3.Classes;
import static com.mycompany.app.week2.code3.Methods.myMethod;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        // exercise 17 - polymorphism
        Unicycle unicycle = new Unicycle();
        Bicycle bicycle = new Bicycle();
        Tricycle tricycle = new Tricycle();

        Cycle[] cycles = { unicycle, bicycle, tricycle };

        for (Cycle cycle : cycles) {
            if (cycle instanceof Unicycle) {
                ((Unicycle) cycle).balance();
            } else if (cycle instanceof Bicycle) {
                ((Bicycle) cycle).balance();
            }
        }

        // exercise 3 - interfaces
        Derived3 d = new Ex3().new Derived3();
        d.print();

        // test methods code 3 w2
        myMethod();
        myMethod();
        myMethod();

        // test class code 3 w2
        Classes c = new Classes();
        System.out.println(c.x);
    }
}