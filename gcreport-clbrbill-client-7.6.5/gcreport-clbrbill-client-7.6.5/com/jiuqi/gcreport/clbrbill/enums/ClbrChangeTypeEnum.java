/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.enums;

public enum ClbrChangeTypeEnum {
    INITIAL("01", "\u6743\u9650\u521d\u59cb"),
    CHANGE("02", "\u6743\u9650\u53d8\u66f4"),
    STOP("03", "\u79bb\u5c97-\u505c\u7528"),
    LEAVE("04", "\u79bb\u5c97-\u4e0d\u505c\u7528");

    private String code;
    private String name;

    private ClbrChangeTypeEnum(String code, String name) {
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

