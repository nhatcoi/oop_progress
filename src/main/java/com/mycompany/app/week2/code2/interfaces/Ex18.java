package com.mycompany.app.week2.code2.interfaces;

public class Ex18 {
    // Define the Cycle interface
    public interface Cycle {
        void ride();
    }

    // Implementations of the Cycle interface
    class Unicycle implements Cycle {
        @Override
        public void ride() {
            System.out.println("Riding a Unicycle");
        }
    }

    class Bicycle implements Cycle {
        @Override
        public void ride() {
            System.out.println("Riding a Bicycle");
        }
    }

    class Tricycle implements Cycle {
        @Override
        public void ride() {
            System.out.println("Riding a Tricycle");
        }
    }

    // Factory interfaces for creating cycles
    public interface CycleFactory {
        Cycle createCycle();
    }

    public class UnicycleFactory implements CycleFactory {
        @Override
        public Cycle createCycle() {
            return new Unicycle();
        }
    }

    public class BicycleFactory implements CycleFactory {
        @Override
        public Cycle createCycle() {
            return new Bicycle();
        }
    }

    public class TricycleFactory implements CycleFactory {
        @Override
        public Cycle createCycle() {
            return new Tricycle();
        }
    }
}
