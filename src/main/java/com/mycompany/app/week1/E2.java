package com.mycompany.app.week1;

import java.util.Random;

public class E2 {
    public static void main(String[] args) {
        int numValues = 25;
        int[] randomValues = generateRandomValues(numValues);
        int referenceValue = generateRandomValue();

        System.out.println("Generated random values:");
        for (int value : randomValues) {
            System.out.print(value + " ");
        } // Nhat

        System.out.println("\nReference value: " + referenceValue);

        classifyValues(randomValues, referenceValue);
    }

    private static int[] generateRandomValues(int numValues) {
        Random random = new Random();
        int[] values = new int[numValues];

        for (int i = 0; i < numValues; i++) {
            values[i] = random.nextInt(25) + 1;
        }

        return values;
    }

    private static int generateRandomValue() {
        Random random = new Random();
        return random.nextInt(25) + 1;
    }

    private static void classifyValues(int[] values, int referenceValue) {
        System.out.println("\nClassifications:");

        for (int value : values) {
            if (value > referenceValue) {
                System.out.println(value + " is greater than the reference value.");
            } else if (value < referenceValue) {
                System.out.println(value + " is less than the reference value.");
            } else {
                System.out.println(value + " is equal to the reference value.");
            }
        }
    }
}
