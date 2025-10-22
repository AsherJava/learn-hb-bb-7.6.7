/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.facade.obj;

public enum ImpFailType {
    NO_ACCESS(1, "\u5355\u4f4d\u5bf9\u8868\u5355\u65e0\u6743\u9650"),
    CHECK_FAIL(2, "\u51fa\u9519\u8bf4\u660e\u5185\u5bb9\u68c0\u67e5\u4e0d\u901a\u8fc7"),
    OUT_OF_RANGE(3, "\u4e0d\u5728\u5bfc\u5165\u8303\u56f4\u5185");

    private final int code;
    private final String desc;

    private ImpFailType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}

