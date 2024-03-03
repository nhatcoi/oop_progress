package com.mycompany.app.week2.interfaces;

public class Ex12 {
    interface CanFight {
        void fight();
    }
    interface CanSwim {
        void swim();
    }
    interface CanFly {
        void fly();
    }

    // add an interface called CanClimb
    interface CanClimb{
        void climb();
    }
    static class ActionCharacter {
        public void fight() {}
    }
    public static class Hero extends ActionCharacter
            implements CanFight, CanSwim, CanFly, CanClimb {
        public void swim() {}
        public void fly() {}
        public void climb() {}
    }
    public class Adventure {
        public static void t(CanFight x) { x.fight(); }
        public static void u(CanSwim x) { x.swim(); }

        public static void v(CanFly x) { x.fly(); }
        public static void c(CanClimb x) { x.climb(); }
        public static void w(ActionCharacter x) { x.fight(); }
    }
}
