package com.mycompany.app.week2.code2.interfaces;

public class Ex4 {
    public abstract class Base4 {
        public abstract void method();
    }

    public class Derived4 extends Base4 {
        @Override
        public void method() {
            System.out.println("Derived method");
        }
    }
}
