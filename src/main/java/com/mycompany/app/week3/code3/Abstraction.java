package com.mycompany.app.week3.code3;

public class Abstraction {
    abstract class Animal {
        // Abstract method (does not have a body)
        public abstract void animalSound();

        // Regular method
        public void sleep() {
            System.out.println("Zzz");
        }
    }

    // Subclass (inherit from Animal)
    public class Pig extends Animal {
        public void animalSound() {
            // The body of animalSound() is provided here
            System.out.println("The pig says: wee wee");
        }
    }
}
