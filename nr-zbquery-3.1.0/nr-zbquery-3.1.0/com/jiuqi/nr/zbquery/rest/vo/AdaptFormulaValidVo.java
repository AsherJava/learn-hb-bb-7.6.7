/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

public class AdaptFormulaValidVo {
    private boolean valid;
    private String message;
    private int resultType;

    public AdaptFormulaValidVo(boolean valid, String message, int resultType) {
        this.valid = valid;
        this.message = message;
        this.resultType = resultType;
    }

    public static AdaptFormulaValidVo success(int resultType) {
        return new AdaptFormulaValidVo(true, null, resultType);
    }

    public static AdaptFormulaValidVo error(String message) {
        return new AdaptFormulaValidVo(false, message, -1);
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultType() {
        return this.resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }
}

