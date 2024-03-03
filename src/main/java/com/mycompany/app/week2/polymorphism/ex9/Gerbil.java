package com.mycompany.app.week2.polymorphism.ex9;

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

        @Override
        public String name(String n) {
            return n;
        }

        @Override
        public int age(int months) {
            return months;
        }
}
