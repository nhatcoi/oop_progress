package com.mycompany.app.week2.code2.interfaces;

// Make the methods of Rodent abstract whenever possible.
public class Ex1 {
    public abstract class Rodent {
        public abstract void eat();

        public abstract void run();

        public abstract void sleep();
    }

    public class Gerbil extends Rodent {
        @Override
        public void eat() {
            System.out.println("Gerbil is gobble");
        }

        @Override
        public void run() {
            System.out.println("Gerbil running and jumping");
        }

        @Override
        public void sleep() {
            System.out.println("Gerbil is sleeping");
        }
    }

    public class Hamster extends Rodent {

        @Override
        public void eat() {
            System.out.println("Hamster is gobble");
        }

        @Override
        public void run() {
            System.out.println("Hamster run slow");
        }

        @Override
        public void sleep() {
            System.out.println("Hamster is sleeping");
        }

    }

    public class Mouse extends Rodent {

        @Override
        public void eat() {
            System.out.println("Mouse is gobble");
        }

        @Override
        public void run() {
            System.out.println("Mouse run fast");
        }

        @Override
        public void sleep() {
            System.out.println("Mouse is sleeping");
        }
    }

}