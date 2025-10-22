/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.input;

public enum ImportErrorTypeEnum {
    NO_WRITE_ACCESS(1, "\u6ca1\u6709\u5199\u5165\u6743\u9650"),
    REGION_READ_ONLY(2, "\u533a\u57df\u53ea\u8bfb"),
    ERROR_DATA(3, "\u6307\u6807\u6570\u636e\u975e\u6cd5");

    private int errorCode;
    private String errorMessage;

    private ImportErrorTypeEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

