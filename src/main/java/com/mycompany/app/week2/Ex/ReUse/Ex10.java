package com.mycompany.app.week2.Ex.ReUse;

public class Ex10 {
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

        public Root(Component1 c1, Component2 c2, Component3 c3) {
            component1 = c1;
            component2 = c2;
            component3 = c3;
            System.out.println("Constructor Root");
        }
    }

    public class Stem extends Root {
        private Component1 component1;
        private Component2 component2;
        private Component3 component3;

        public Stem(Component1 c1, Component2 c2, Component3 c3) {
            super(c1, c2, c3);
            component1 = c1;
            component2 = c2;
            component3 = c3;
            System.out.println("Constructor Stem");
        }
    }

}
