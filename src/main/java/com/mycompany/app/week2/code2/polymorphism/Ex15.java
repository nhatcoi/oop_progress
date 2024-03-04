package com.mycompany.app.week2.code2.polymorphism;

public class Ex15 {
    class Glyph {
        void draw() {
            System.out.println("Glyph.draw()"); }
        Glyph() {
            System.out.println("Glyph() before draw()");
            draw();
            System.out.println("Glyph() after draw()");
        }
    }
    public class RoundGlyph extends Glyph {
        private int radius = 1;
        public RoundGlyph(int r) {
            radius = r;
            System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
        }
        void draw() {
            System.out.println("RoundGlyph.draw(), radius = " + radius);
        }
    }

    public class RectangularGlyph extends Glyph {
        private int length = 2;
        private int width = 3;
        public RectangularGlyph(int l, int w) {
            length = l;
            width = w;
            System.out.println("RectangularGlyph.RectangularGlyph(), length = " + length + ", width = " + width);
        }
        void draw() {
            System.out.println("RectangularGlyph.draw(), length = " + length + ", width = " + width);
        }
    }
}
