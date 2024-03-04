package com.mycompany.app.week2.code2.cleanup;

public class Ex2 {
    class StringID {
        private String iStringD = "Initialized at definition";

        public String getIStringD() {
            return iStringD;
        }
    }

    class StringIC {
        private String iStringC;

        public StringIC(String iStringC) {
            this.iStringC = iStringC;
        }

        public String getIStringC() {
            return iStringC;
        }
    }
    // 1 cái khởi tạo luôn
    // 1 cái gọi ra từ constructor rồi mới khởi tạo giá trị
}
