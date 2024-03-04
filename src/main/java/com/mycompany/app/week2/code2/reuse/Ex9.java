package com.mycompany.app.week2.code2.reuse;

public class Ex9 {
    class Component1 {
        public Component1() {
            System.out.println("Component1");
        }
    }

    class Component2 {
        public Component2() {
            System.out.println("Component2");
        }
    }

    class Component3 {
        public Component3() {
            System.out.println("Component3");
        }
    }

    public class Root {
        private Component1 component1;
        private Component2 component2;
        private Component3 component3;

        public Root() {
            component1 = new Component1();
            component2 = new Component2();
            component3 = new Component3();
            System.out.println("Constructor Root");
        }
    }

    public class Stem extends Root {
        private Component1 component1;
        private Component2 component2;
        private Component3 component3;

        public Stem() {
            component1 = new Component1();
            component2 = new Component2();
            component3 = new Component3();
            System.out.println("Constructor Stem");
        }
    }

}
