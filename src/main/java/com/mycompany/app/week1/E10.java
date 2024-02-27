package com.mycompany.app.week1;

public class E10 {
    public static void main(String[] args) {
        for (int i = 10; i < 100; i++) {
            for (int j = i; j < 100; j++) {
                int product = i * j;
                if (product < 1000 || product > 9999)
                    continue;
                String productStr = String.valueOf(product);
                String factorsStr = String.valueOf(i) + String.valueOf(j);
                char[] productChars = productStr.toCharArray();
                char[] factorsChars = factorsStr.toCharArray();
                java.util.Arrays.sort(productChars);
                java.util.Arrays.sort(factorsChars);
                if (java.util.Arrays.equals(productChars, factorsChars)) {
                    System.out.println(product + " = " + i + " * " + j);
                }
            }
        }
    }
}
