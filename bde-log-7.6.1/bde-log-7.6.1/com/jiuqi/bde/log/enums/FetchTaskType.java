/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.log.enums;

public enum FetchTaskType {
    NR_BATCH_FETCH("NR_BATCH_FETCH", "\u62a5\u8868\u53d6\u6570"),
    NR_BATCH_FETCH_UNIT("NR_BATCH_FETCH_UNIT", "\u6279\u91cf\u53d6\u6570\u62c6\u5206"),
    NR_FETCH("NR_FETCH", "\u62a5\u8868\u53d6\u6570"),
    BILL_FETCH("BILL_FETCH", "\u5355\u636e\u53d6\u6570"),
    NR_FETCH_BDE_EXECUTE("FETCH_BDE_EXECUTE", "\u4e1a\u52a1\u6570\u636e\u5f15\u64ce\u53d6\u6570\u6267\u884c"),
    NR_FETCH_BDE_COMPUT("FETCH_BDE_COMPUT", "\u62a5\u8868\u53d6\u6570");

    private final String code;
    private final String title;

    private FetchTaskType(String code, String title) {
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

