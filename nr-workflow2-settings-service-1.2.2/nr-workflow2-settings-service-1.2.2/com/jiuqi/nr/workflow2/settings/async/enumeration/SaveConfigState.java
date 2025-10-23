/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.async.enumeration;

public enum SaveConfigState {
    ACTIVE("\u8fdb\u884c\u4e2d"),
    SUCCESS("\u6210\u529f"),
    ERROR("\u5931\u8d25");

    final String title;

    private SaveConfigState(String title) {
        this.title = title;
    }
}

