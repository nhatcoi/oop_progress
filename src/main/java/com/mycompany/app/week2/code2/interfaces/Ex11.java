package com.mycompany.app.week2.code2.interfaces;

public class Ex11 {
    interface Processor {
        String process(String input);
    }

    public static class Apply {
        public static void process(Processor p, String s) {
            System.out.println(p.process(s));
        }
    }

    public static class SwapPairs implements Processor {
        @Override
        public String process(String input) {
            StringBuilder result = new StringBuilder(input);
            for (int i = 0; i < result.length() - 1; i += 2) {
                char temp = result.charAt(i);
                result.setCharAt(i, result.charAt(i + 1));
                result.setCharAt(i + 1, temp);
            }
            return result.toString();
        }
    }
}
