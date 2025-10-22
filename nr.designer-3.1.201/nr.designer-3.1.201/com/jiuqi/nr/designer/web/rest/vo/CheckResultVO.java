/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

public class CheckResultVO {
    public static final String UNIQUE = "\u6307\u6807\u6216\u5b57\u6bb5\u6821\u9a8c\u901a\u8fc7\uff01";
    public static final String UN_UNIQUE = "\u6307\u6807\u6216\u5b57\u6bb5\u6807\u8bc6\u4e0d\u552f\u4e00\uff01";
    private boolean unique;
    private String message;

    public CheckResultVO() {
    }

    public CheckResultVO(boolean unique, String message) {
        this.unique = unique;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }
}

