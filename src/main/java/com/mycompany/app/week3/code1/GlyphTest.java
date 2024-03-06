package com.mycompany.app.week3.code1;

public class GlyphTest {
    abstract class Glyph {
        abstract void draw();
        public Glyph() {
            System.out.println("Glyph() before draw");
            draw();
            System.out.println("Glyph() after draw");
        }
    }

    public class RoundGlyph extends Glyph {
        int radius = 1;

        public RoundGlyph(int r) {
            radius = r;
            System.out.println("RoundGlyph(), radius=" + radius);
        }

        void draw() {
            System.out.println("RoundGlyph.draw(), radius=" + radius);
        }
    }
}
