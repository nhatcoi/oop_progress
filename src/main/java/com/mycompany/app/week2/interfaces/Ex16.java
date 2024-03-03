package com.mycompany.app.week2.interfaces;
import java.util.Scanner;
import java.nio.CharBuffer;
import java.io.IOException;
import java.io.Reader;

// class adapt this class so that it can be an input to a Scanner object.
public class Ex16 {


    public static class CharSequenceReader extends Reader {
        private String charSequence;
        private int length;
        private int next = 0;

        public CharSequenceReader(String charSequence) {
            this.charSequence = charSequence;
            this.length = charSequence.length();
        }

        @Override
        public int read(CharBuffer target) throws IOException {
            if (next >= length) return -1;
            target.append(charSequence.charAt(next++));
            return 1;
        }

        @Override
        public int read() throws IOException {
            if (next >= length) return -1;
            return charSequence.charAt(next++);
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            return 0;
        }

        @Override
        public void close() throws IOException {
            // Nothing to close
        }
    }
}
