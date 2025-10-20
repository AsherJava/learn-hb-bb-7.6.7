/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.intf;

public enum OptimizeOperator {
    EQ("=", "\u7b49\u4e8e"),
    LIKE("LIKE", "\u6a21\u7cca\u5339\u914d");

    private final String code;
    private final String title;

    private OptimizeOperator(String code, String title) {
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

