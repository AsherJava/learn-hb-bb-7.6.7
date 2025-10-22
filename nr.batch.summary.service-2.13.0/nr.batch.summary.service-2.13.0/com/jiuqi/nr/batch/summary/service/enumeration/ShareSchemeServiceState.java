/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.enumeration;

public enum ShareSchemeServiceState {
    NO_SELECTED("fail", "\u672a\u9009\u62e9\u6c47\u603b\u65b9\u6848"),
    SUCCESS("success", "\u6210\u529f"),
    FAIL("fail", "\u5931\u8d25");

    public String code;
    public String title;

    private ShareSchemeServiceState(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

