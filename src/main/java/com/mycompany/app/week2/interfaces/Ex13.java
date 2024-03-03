package com.mycompany.app.week2.interfaces;

public interface Ex13 {
    interface Interface1 {
        void method1();
    }

    interface Interface2 extends Interface1 {
        void method2();
    }

    interface Interface3 extends Interface1 {
        void method3();
    }

    interface Interface4 extends Interface2, Interface3 {
        void method4();
    }
}
