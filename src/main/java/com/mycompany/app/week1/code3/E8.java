package com.mycompany.app.week1.code3;

public class E8 {
    public void Ex8() {

        for (int i = 1; i <= 3; i++) {
            switch (i) {
                case 1:
                    System.out.println("Case 1");
                    break;
                case 2:
                    System.out.println("Case 2");
                    break;
                case 3:
                    System.out.println("Case 3");
                    break;
                default:
                    break;
            }
        }

        System.out.println("Without break");
        // without break
        for (int i = 1; i <= 3; i++) {
            switch (i) {
                case 1:
                    System.out.println("Case 1");

                case 2:
                    System.out.println("Case 2");

                case 3:
                    System.out.println("Case 3");

            }
        }
    }
}
