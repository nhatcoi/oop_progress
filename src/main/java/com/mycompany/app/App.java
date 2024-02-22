/*----------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 *---------------------------------------------------------------------------------------*/

package com.mycompany.app;

public class App {
    public static class Number {
        public int i;
    }
    static void f(Number m) {
        m.i = 15;
        }
        public static void main(String[] args) {
        Number n = new Number();
        n.i = 14;
        f(n);
        System.out.println(n.i);
    }
}
