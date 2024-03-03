package com.mycompany.app.week2.interfaces;
import com.mycompany.app.week2.polymorphism.ex6_7_8.Note;

public class Ex10 {
    interface Playable {
        void play(Note n);
    }

    abstract class Instrument {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }

        void adjust() {
            System.out.println(this + ".adjust()");
        }
    }

    class Wind extends Instrument implements Playable {
        @Override
        public void play(Note n) {
            System.out.println(this + ".play() " + n);
        }
        // Wind specific methods...
    }

    class Percussion extends Instrument implements Playable {
        @Override
        public void play(Note n) {
            System.out.println(this + ".play() " + n);
        }
        // Percussion specific methods...
    }

    class Stringed extends Instrument implements Playable {
        @Override
        public void play(Note n) {
            System.out.println(this + ".play() " + n);
        }
        // Stringed specific methods...
    }

    public class Musics {
        static void tune(Playable p) {
            p.play(new Note());
        }

        public void main(String[] args) {
            Wind flute = new Wind();
            Percussion drum = new Percussion();
            Stringed violin = new Stringed();

            tune(flute);
            tune(drum);
            tune(violin);
        }
    }
}
