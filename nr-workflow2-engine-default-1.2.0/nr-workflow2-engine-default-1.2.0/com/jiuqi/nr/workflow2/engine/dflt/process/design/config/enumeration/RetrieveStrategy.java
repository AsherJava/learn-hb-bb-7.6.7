/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration;

public enum RetrieveStrategy {
    RETRIEVE_SELF("\u81ea\u884c\u53d6\u56de", "act_retrieve"),
    APPLY_RETURN("\u7533\u8bf7\u9000\u56de", "act_apply_reject");

    public final String title;
    public final String code;

    private RetrieveStrategy(String title, String code) {
        this.title = title;
        this.code = code;
    }
}

