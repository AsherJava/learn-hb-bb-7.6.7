/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.constant;

public enum ErrorCode {
    SYSTEMERROR(0, "\u7cfb\u7edf\u9519\u8bef"),
    FILEERROR(1, "\u6587\u4ef6\u9519\u8bef"),
    SHEETERROR(2, "sheet\u9875\u9519\u8bef"),
    REPORTERROR(3, "\u62a5\u8868\u9519\u8bef"),
    REGIONERROR(4, "\u533a\u57df\u9519\u8bef"),
    DATAERROR(5, "\u5355\u5143\u683c\u9519\u8bef");

    private int errorCodeType;
    private String errorCodeMsg;

    private ErrorCode(int errorCodeType, String errorCodeMsg) {
        this.errorCodeType = errorCodeType;
        this.errorCodeMsg = errorCodeMsg;
    }

    public int getErrorCodeType() {
        return this.errorCodeType;
    }

    public void setErrorCodeType(int errorCodeType) {
        this.errorCodeType = errorCodeType;
    }

    public String getErrorCodeMsg() {
        return this.errorCodeMsg;
    }

    public void setErrorCodeMsg(String errorCodeMsg) {
        this.errorCodeMsg = errorCodeMsg;
    }
}

