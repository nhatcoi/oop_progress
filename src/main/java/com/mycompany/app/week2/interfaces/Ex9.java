package com.mycompany.app.week2.interfaces;

import com.mycompany.app.week2.polymorphism.ex6_7_8.Note;

public class Ex9 {
    abstract static class Instrument {
        void play(Note n) {
            System.out.println(this + ".play() " + n);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        void adjust() {
            System.out.println(this + ".adjust()");
        }
    }

    class Wind extends Instrument {
        // Wind specific methods...
    }

    class Percussion extends Instrument {
        // Percussion specific methods...
    }

    class Stringed extends Instrument {
        // Stringed specific methods...
    }
}
