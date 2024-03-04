package com.mycompany.app.week2.code2.cleanup;

public class Ex11 {

    public boolean finalized = false;

    public class InnerEx11 {

        public InnerEx11() {
            System.out.println("Creating an object");
        }

        // Finalize method
        protected void finalize() {
            System.out.println("Finalizing an object");
            finalized = true;
        }

    }

    public void run() {
        InnerEx11 innerEx11 = new InnerEx11();
        innerEx11 = null;
        while (!finalized) {
            System.gc(); // Requesting garbage collection
        }
    }
}
