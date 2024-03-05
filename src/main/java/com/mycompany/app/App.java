package com.mycompany.app;

import com.mycompany.app.Debug.Debug;

import com.mycompany.app.week2.code2.interfaces.Ex3;
import com.mycompany.app.week2.code2.interfaces.Ex3.*;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Bicycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Cycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Tricycle;
import com.mycompany.app.week2.code2.polymorphism.ex1_5_17.Unicycle;
import com.mycompany.app.week2.code3.Classes;
import com.mycompany.app.week3.code2.InterfacesExercise21.InnerInterfacesExercise21;
import com.mycompany.app.week3.code2.InterfacesExercise21.InnerClass;
import com.mycompany.app.week3.code3.Abstraction;
import com.mycompany.app.week3.code3.Polymorphism;
import com.mycompany.app.week3.code3.Polymorphism.*;

import static com.mycompany.app.week2.code3.Methods.myMethod;

public class App {
    static Debug debug = new Debug();

    public static void main(String[] args) {
        System.out.println("Group 17");

        Abstraction abstraction = new Abstraction();
        Abstraction.Pig myPigA = abstraction.new Pig();
        myPigA.animalSound();// Create a Pig object

        Polymorphism polymorphism = new Polymorphism();
        Animal myAnimal = polymorphism.new Animal(); // Create a Animal object
        Animal myPig = polymorphism.new Pig(); // Create a Pig object
        Animal myDog = polymorphism.new Dog(); // Create a Dog object
        myAnimal.animalSound();
        myPig.animalSound();
        myDog.animalSound();

        InnerClass innerClass = new InnerClass();
        InnerInterfacesExercise21.NestedClass.Call(innerClass);

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