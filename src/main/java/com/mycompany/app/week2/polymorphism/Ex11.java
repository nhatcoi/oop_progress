package com.mycompany.app.week2.polymorphism;

// Order of constructor calls.
public class Ex11 {
    static class Meal {
        Meal() {
            System.out.println("Meal()");
        }
    }

    static class Bread {
        Bread() {
            System.out.println("Bread()");
        }
    }

    static class Cheese {
        Cheese() {
            System.out.println("Cheese()");
        }
    }

    static class Lettuce {
        Lettuce() {
            System.out.println("Lettuce()");
        }
    }

    // Add class Pickle to Sandwich
    static class Pickle extends Lunch {
        Pickle() {
            System.out.println("Pickle()");
        }
    }

    static class Lunch extends Meal {
        Lunch() {
            System.out.println("Lunch()");
        }
    }

    static class PortableLunch extends Lunch {
        PortableLunch() {
            System.out.println("PortableLunch()");
        }
    }

    public static class Sandwich extends PortableLunch {
        private Bread b = new Bread();
        private Cheese c = new Cheese();
        private Lettuce l = new Lettuce();
        private Pickle p = new Pickle();

        public Sandwich() {
            System.out.println("Sanwich()");
        }
    }


}
