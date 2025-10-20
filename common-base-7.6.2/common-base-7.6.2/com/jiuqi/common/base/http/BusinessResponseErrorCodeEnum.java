/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.http;

public enum BusinessResponseErrorCodeEnum {
    TOKEN_INVALID("5010001", "\u65e0\u6548\u7684\u8bbf\u95ee\u4ee4\u724c\u3002");

    private String errorCode;
    private String errorTitle;

    private BusinessResponseErrorCodeEnum(String errorCode, String errorTitle) {
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorTitle() {
        return this.errorTitle;
    }
}

