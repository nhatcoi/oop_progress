package com.mycompany.app.week2.Ex.CleanUp;

import com.mycompany.app.Debug.Debug;

public class Ex5 {
    Debug debug = new Debug();

    public class Dog {
        public void bark(int times) {
            for (int i = 0; i < times; i++) {
                debug.debug("Bark!");
            }
        }

        public void bark(double vol) {
            if (vol < 0.5) {
                debug.debug("Soft bark!");
            } else {
                debug.debug("Loud bark!");
            }
        }

        public void bark(String message) {
            debug.debug("bark: " + message);
        }

        public void bark(int times, String message) {
            for (int i = 0; i < times; i++) {
                debug.debug("Bark: " + message);
            }
        }
    }
}
