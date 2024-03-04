package com.mycompany.app.week2.code2.reuse;

public class Ex1 {
    public class First {
        public First() {
            System.out.println("First");
        }

        public void print() {
            System.out.println("First");
        }
    }

    public class Second {
        First instanceFirst;

        public Second() {
            instanceFirst = null;
            System.out.println("Second");
        }

        public First gIFirst() {
            if (instanceFirst == null) {
                instanceFirst = new First();
            }
            return instanceFirst;
        }

    }
}
