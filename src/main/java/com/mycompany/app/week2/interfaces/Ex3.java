package com.mycompany.app.week2.interfaces;

public class Ex3 {
    public abstract class Base {
        Base() {
            print();
        }

        abstract void print();
    }

    public class Derived3 extends Base {
        private int i = 5;

        @Override
        public void print() {
            System.out.println("i = " + i);
        }
    }
}
