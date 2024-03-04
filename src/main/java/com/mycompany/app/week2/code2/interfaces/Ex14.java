package com.mycompany.app.week2.code2.interfaces;

public class Ex14 {
    public interface Interface1 {
        void method1();
        void method2();
    }

    public interface Interface2 {
        void method3();
        void method4();
    }

    public interface Interface3 {
        void method5();
        void method6();
    }

    public interface CombinedInterface extends Interface1, Interface2, Interface3 {
        void newMethod();
    }

    static class ConcreteClass {
        public void concreteMethod() {
            System.out.println("Concrete method");
        }
    }

    public static class NewClass extends ConcreteClass implements CombinedInterface {
        @Override
        public void method1() {
            System.out.println("Method 1");
        }

        @Override
        public void method2() {
            System.out.println("Method 2");
        }

        @Override
        public void method3() {
            System.out.println("Method 3");
        }

        @Override
        public void method4() {
            System.out.println("Method 4");
        }

        @Override
        public void method5() {
            System.out.println("Method 5");
        }

        @Override
        public void method6() {
            System.out.println("Method 6");
        }

        @Override
        public void newMethod() {
            System.out.println("New method");
        }
    }
}
