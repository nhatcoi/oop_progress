package com.mycompany.app.week2.code2.reuse;

public class Ex5 {
    public class A {
        public A() {
            System.out.println("Call A");
        }
    }

    public class B {
        public B() {
            System.out.println("Call B");
        }
    }

    public class C extends A {
        B bMember = new B();
    }
}
