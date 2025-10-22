/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.offsetvoucher.utils;

import java.util.Objects;

public enum OffsetMethodEnum {
    AUTO("AUTO", "\u81ea\u52a8\u62b5\u9500"),
    ADJUST("ADJUST", "\u624b\u5de5\u8c03\u6574"),
    NOOFFSET("NOOFFSET", "\u4e0d\u6d89\u53ca\u79d1\u76ee");

    private String code;
    private String name;

    private OffsetMethodEnum(String code, String title) {
        this.code = code;
        this.name = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static OffsetMethodEnum codeOf(String code) {
        for (OffsetMethodEnum OffsetMethod : OffsetMethodEnum.values()) {
            if (!Objects.equals(OffsetMethod.getCode(), code)) continue;
            return OffsetMethod;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684OffsetMethodEnum\u679a\u4e3e:" + code);
    }

    public static OffsetMethodEnum nameOf(String name) {
        for (OffsetMethodEnum OffsetMethod : OffsetMethodEnum.values()) {
            if (!Objects.equals(OffsetMethod.getName(), name)) continue;
            return OffsetMethod;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684OffsetMethodEnum\u679a\u4e3e:" + name);
    }
}

