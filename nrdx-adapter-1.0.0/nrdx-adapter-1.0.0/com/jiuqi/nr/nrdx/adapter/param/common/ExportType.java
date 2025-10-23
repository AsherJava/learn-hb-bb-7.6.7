/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.adapter.param.common;

public enum ExportType {
    ONLY_DATA("ONLY_DATA", "\u4ec5\u6570\u636e"),
    ONLY_PARAM("ONLY_PARAM", "\u4ec5\u53c2\u6570"),
    DATA_PARAM("DATA_PARAM", "\u6570\u636e\u548c\u53c2\u6570");

    private final String code;
    private final String title;

    private ExportType(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

