package com.mycompany.app.week2.polymorphism;

//: polymorphism/Transmogrify.java
// Dynamically changing the behavior of an object
// via composition (the "State" design pattern).
public class Ex16 {
    class Actor {
        public void act() {}
    }
    class HappyActor extends Actor {
        public void act() {
            System.out.println("HappyActor"); }
    }
    class SadActor extends Actor {
        public void act() { System.out.println("SadActor"); }
    }
    class Stage {
        private Actor actor = new HappyActor();
        public void change() { actor = new SadActor(); }
        public void performPlay() { actor.act(); }
    }


    // create a Starship class
    abstract class AlertStatus {
        abstract void alert();
    }

    class GreenAlert extends AlertStatus {
        void alert() {
            System.out.println("Green Alert");
        }
    }

    class YellowAlert extends AlertStatus {
        void alert() {
            System.out.println("Yellow Alert");
        }
    }

    class RedAlert extends AlertStatus {
        void alert() {
            System.out.println("Red Alert");
        }
    }

    public class Starship {
        public Starship() {}

        AlertStatus alertStatus = new GreenAlert();

        public void toGreenAlert() {
            alertStatus = new GreenAlert();
        }

        public void toYellowAlert() {
            alertStatus = new YellowAlert();
        }

        public void toRedAlert() {
            alertStatus = new RedAlert();
        }

        public void performAlert() {
            alertStatus.alert();
        }
    }
}
