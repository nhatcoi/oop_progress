package com.mycompany.app.week2.code2.interfaces;
import java.util.Random;
public class Ex19 {

    // representing the outcome of a toss
    public abstract static class TossOutcome {
        public abstract void displayOutcome();
    }

    // Concrete classes representing different outcomes of a coin toss
    class CoinTossOutcome extends TossOutcome {
        private final String result;

        CoinTossOutcome(String result) {
            this.result = result;
        }

        @Override
        public void displayOutcome() {
            System.out.println("Coin Toss Result: " + result);
        }
    }

    // representing different outcomes of a dice toss
    class DiceTossOutcome extends TossOutcome {
        private final int result;

        DiceTossOutcome(int result) {
            this.result = result;
        }

        @Override
        public void displayOutcome() {
            System.out.println("Dice Toss Result: " + result);
        }
    }

    // for the TossFactory
    public abstract class TossFactory {
        public abstract TossOutcome createTossOutcome();
    }

    //the CoinTossFactory
    public class CoinTossFactory extends TossFactory {
        @Override
        public TossOutcome createTossOutcome() {
            Random random = new Random();
            String result = (random.nextBoolean()) ? "Heads" : "Tails";
            return new CoinTossOutcome(result);
        }
    }

    // for the DiceTossFactory
    public class DiceTossFactory extends TossFactory {
        @Override
        public TossOutcome createTossOutcome() {
            Random random = new Random();
            int result = random.nextInt(6) + 1; // Generating a random number between 1 and 6
            return new DiceTossOutcome(result);
        }
    }
}
