package com.mycompany.app.week2.Ex.ReUse;

public class Ex2 {
    public class Detergent {
        public void scrub() {
            System.out.println("Detergent.scrub()");
        }

        public void foam() {
            System.out.println("Detergent.foam()");
        }
    }

    public class newDetergent extends Detergent {
        public void scrub() {
            System.out.println("newDetergent.scrub()");
        }

        public void sterilize() {
            System.out.println("newDetergent.sterilize()");
        }

    }
}
