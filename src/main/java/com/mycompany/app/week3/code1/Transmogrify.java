package com.mycompany.app.week3.code1;

public class Transmogrify {
    abstract class Actor {
        abstract void act();
    }

    public class HappyActor extends Actor {
        public void act() {
            System.out.println("HappyActor");
        }
    }

    public class SadActor extends Actor {
        public void act() {
            System.out.println("SadActor");
        }
    }
    public class Stage {
        public Actor a = new HappyActor();

        public void change() {
            a = new SadActor();
        }

        public void go() {
            a.act();
        }
    }
}
