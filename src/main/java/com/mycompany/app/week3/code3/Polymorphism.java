package com.mycompany.app.week3.code3;

public class Polymorphism {
    public class Animal {
        public void animalSound() {
            System.out.println("The animal makes a sound");
        }
    }

    public class Pig extends Animal {
        public void animalSound() {
            System.out.println("The pig says: wee wee");
        }
    }

    public class Dog extends Animal {
        public void animalSound() {
            System.out.println("The dog says: bow wow");
        }
    }
}
