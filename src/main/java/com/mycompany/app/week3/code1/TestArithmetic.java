package com.mycompany.app.week3.code1;

public class TestArithmetic {
    public class Node {
        public Node() {}
        public double eval() {
            System.out.println("Error: eval Node");
            return 0;
        }
    }
    public class Binop extends Node {
        protected Node lChild, rChild;
        public Binop(Node l, Node r) {
            lChild = l;
            rChild = r;
        }
    }
        public class Plus extends Binop {
        public Plus(Node l, Node r) {
            super(l, r);// l, r of Binop
        }
        public double eval() {
            return lChild.eval() + rChild.eval(); //protected Note can
            //Accessed by subclass
        }
    }
    public class Const extends Node {
        private double value;
        public Const(double d) { value = d; }
        public double eval() { return value; }
    }
}
