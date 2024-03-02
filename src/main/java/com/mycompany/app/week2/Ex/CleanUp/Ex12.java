package com.mycompany.app.week2.Ex.CleanUp;

public class Ex12 {
    public class Tank {
        private boolean isEmpty;

        public Tank() {
            isEmpty = true;
        }

        public void fill() {
            isEmpty = false;
        }

        public void empty() {
            isEmpty = true;
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public void finalize() {
            if (!isEmpty) {
                System.out.println("Error: Tank not empty");
            } else {
                System.out.println("Tank is empty");
            }
        }
    }
}
