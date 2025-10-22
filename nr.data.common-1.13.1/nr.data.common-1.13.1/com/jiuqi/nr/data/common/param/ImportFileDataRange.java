/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.param;

public enum ImportFileDataRange {
    REPORT("report", "\u62a5\u8868"),
    UNIT("unit", "\u5355\u4f4d"),
    REGION("region", "\u533a\u57df");

    private final String code;
    private final String title;

    private ImportFileDataRange(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

