package com.mycompany.app.week2.code2.reuse;

public class Ex8 {
    public class Base {
        private int baseValue;

        public Base(int baseValue) {
            this.baseValue = baseValue;
            System.out.println("Constructor Base" + baseValue);
        }

        public int getBaseValue() {
            return baseValue;
        }
    }

    public class Derived extends Base {
        private int derivedValue;

        // Hàm tạo mặc định của lớp dẫn xuất
        public Derived() {
            super(0);
            System.out.println("Constructor Defalut");
        }

        // Hàm tạo không mặc định của lớp dẫn xuất
        public Derived(int baseValue, int derivedValue) {
            super(baseValue);
            this.derivedValue = derivedValue;
            System.out.println("Constructor " + derivedValue);
        }

        public int getDerivedValue() {
            return derivedValue;
        }
    }
}
