package com.mycompany.app.week3.code1;

public class Transmogrify {
    abstract class Actor {
        abstract void act();
    }

    public class HappyActor extends Actor {
        public void act() { //…}
        }

        public class SadActor extends Actor {
            public void act() { //…}
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
    }
}
