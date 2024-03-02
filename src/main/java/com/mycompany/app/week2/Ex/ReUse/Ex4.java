package com.mycompany.app.week2.Ex.ReUse;

public class Ex4 {
    public class Base {
        public Base() {
            System.out.println("Base");
        }
    }

    public class derived extends Base {
        public derived() {
            System.out.println("derived");
        }
    }
}
