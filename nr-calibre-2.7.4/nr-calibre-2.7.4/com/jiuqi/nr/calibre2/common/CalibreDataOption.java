/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.common;

public class CalibreDataOption {

    public static enum DataType {
        LIST(0),
        EXPRESS(1),
        SUM_EXPRESS(2);

        private int code;

        private DataType(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static enum DataTreeType {
        ROOT,
        DIRECT_CHILDREN;

    }
}

