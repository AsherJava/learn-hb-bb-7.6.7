/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.impl.enums;

public enum RefHandleStatus {
    PENDING("0", "\u5f85\u5904\u7406"),
    CREATED("1", "\u65b0\u589e"),
    UPDATED("2", "\u4fee\u6539"),
    IMPORTED("3", "\u5bfc\u5165"),
    DELETED("4", "\u5220\u9664");

    private String code;
    private String name;

    private RefHandleStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public static RefHandleStatus[] listAllStatus() {
        return RefHandleStatus.values();
    }

    public static String getNameByCode(String code) {
        for (RefHandleStatus status : RefHandleStatus.values()) {
            if (!status.getCode().equals(code)) continue;
            return status.name;
        }
        return null;
    }
}

