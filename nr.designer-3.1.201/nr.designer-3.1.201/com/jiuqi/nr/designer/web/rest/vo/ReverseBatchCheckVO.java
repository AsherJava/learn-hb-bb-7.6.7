/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class ReverseBatchCheckVO {
    private String code;
    private boolean checkResult;
    private String message;

    public static ReverseBatchCheckVO getSuccess(String code, boolean checkResult) {
        if (checkResult) {
            return new ReverseBatchCheckVO(code, checkResult, "\u6821\u9a8c\u901a\u8fc7");
        }
        return new ReverseBatchCheckVO(code, checkResult, "\u6807\u8bc6\u4e0d\u552f\u4e00");
    }

    public ReverseBatchCheckVO(String code, boolean checkResult, String message) {
        this.code = code;
        this.checkResult = checkResult;
        this.message = message;
    }

    public ReverseBatchCheckVO() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(boolean checkResult) {
        this.checkResult = checkResult;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

