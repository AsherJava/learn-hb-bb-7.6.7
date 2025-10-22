/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.enums;

public enum GenerateWay {
    SIMPLE_COPY("simpleCopy", "\u6309\u53c2\u8003\u7248\u672c\u751f\u6210"),
    DYNAMIC_COPY("dynamicCopy", "\u6309\u76ee\u6807\u7248\u672c\u751f\u6210");

    private final String code;
    private final String name;

    private GenerateWay(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

