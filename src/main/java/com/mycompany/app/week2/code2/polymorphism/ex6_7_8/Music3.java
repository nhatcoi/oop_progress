package com.mycompany.app.week2.code2.polymorphism.ex6_7_8;

import java.util.Random;

public class Music3 {
    // Doesn’t care about type, so new types
    // added to the system still work right:
    public static void tune(Instrument i) {
        // ...
        Note note = new Note();
        Random rand = new Random();
        int randomNote = rand.nextInt(7);
        i.play(note, randomNote);
    }

    public static void tuneAll(Instrument[] e) {
        for(Instrument i : e)
            tune(i);
    }


    public static void toString(Instrument[] i) {
        for (Instrument instrument : i) {
            System.out.println(instrument.what());
        }
    }
}
