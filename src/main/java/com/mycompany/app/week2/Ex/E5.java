package com.mycompany.app.week2.Ex;

import com.mycompany.app.Debug.Debug;

public class E5 {
    Debug debug = new Debug();
    public int publicField = 10;
    private int privateField = 20;
    protected int protectedField = 30;

    public void publicMethod() {
        debug.debug("This is a public method");
    }

    private void privateMethod() {
        debug.debug("This is a private method");
    }

    protected void protectedMethod() {
        debug.debug("This is a protected method");
    }
}
