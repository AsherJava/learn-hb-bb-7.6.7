/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum OpenWayEnum {
    FUNCTAB("FUNCTAB", "\u65b0\u9875\u7b7e"),
    MODELWINDOW("MODELWINDOW", "\u6a21\u6001\u7a97\u53e3"),
    OPENBLANK("OPENBLANK", "\u6d4f\u89c8\u5668\u7a97\u53e3");

    private final String code;
    private final String title;

    private OpenWayEnum(String code, String name) {
        this.code = code;
        this.title = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static OpenWayEnum fromCode(String code) {
        for (OpenWayEnum openWay : OpenWayEnum.values()) {
            if (!openWay.getCode().equals(code)) continue;
            return openWay;
        }
        return null;
    }

    public static OpenWayEnum fromName(String name) {
        for (OpenWayEnum openWay : OpenWayEnum.values()) {
            if (!openWay.getTitle().equals(name)) continue;
            return openWay;
        }
        return null;
    }
}

