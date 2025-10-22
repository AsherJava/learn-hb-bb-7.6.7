/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.offsetcheck.enums;

public enum CheckStatusEnum {
    CHECK_CONSISTENT("checkConsistent", "\u68c0\u67e5\u4e00\u81f4"),
    CHECK_INCONSISTENT("checkInconsistent", "\u68c0\u67e5\u4e0d\u4e00\u81f4"),
    UNGENERATED("ungenerated", "\u672a\u751f\u6210");

    private String code;
    private String name;

    private CheckStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

