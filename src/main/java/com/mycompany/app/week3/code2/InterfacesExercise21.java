package com.mycompany.app.week3.code2;

public class InterfacesExercise21 {
    public interface InnerInterfacesExercise21 {
        void test();

        class NestedClass {
            public static void Call(InnerInterfacesExercise21 iExercise21) {
                System.out.println("NestedClass.test()");
                iExercise21.test();
            }
        }
    }

    public static class InnerClass implements InnerInterfacesExercise21 {
        @Override
        public void test() {
            System.out.println("InnerClass.test()");
        }
    }
}
