package com.mycompany.app.week2.code2.interfaces;

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
        public void t(CanFight x) { x.fight(); }
        public void u(CanSwim x) { x.swim(); }

        public void v(CanFly x) { x.fly(); }
        public void c(CanClimb x) { x.climb(); }
        public void w(ActionCharacter x) { x.fight(); }
    }
}
