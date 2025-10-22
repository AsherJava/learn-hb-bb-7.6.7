/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.common;

public class GcBillConsts {
    public static final String SUBTABLENAME = "subTableName";
    public static final String SRCTYPE = "SRCTYPE";

    public static enum FINISHStatus {
        DONE(1, "\u5df2\u5b8c\u6210"),
        UN_DO(0, "");

        private Integer intVal;
        private String content;

        private FINISHStatus(Integer intVal, String content) {
            this.intVal = intVal;
            this.content = content;
        }

        public int getIntVal() {
            return this.intVal;
        }

        public void setIntVal(int intVal) {
            this.intVal = intVal;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

