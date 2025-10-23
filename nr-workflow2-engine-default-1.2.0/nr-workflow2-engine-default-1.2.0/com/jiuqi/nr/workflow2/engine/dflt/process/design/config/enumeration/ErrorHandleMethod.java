/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration;

public enum ErrorHandleMethod {
    NEGLIGIBLE("\u53ef\u5ffd\u7565"),
    DESCRIPTION_REQUIRED("\u9700\u586b\u5199\u8bf4\u660e"),
    APPROVAL_REQUIRED("\u5fc5\u987b\u5ba1\u6838\u901a\u8fc7");

    public final String title;

    private ErrorHandleMethod(String title) {
        this.title = title;
    }
}

