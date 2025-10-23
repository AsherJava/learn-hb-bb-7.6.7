/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.adapter.param.common;

public enum TransferMode {
    FULL_MODE("FULL_MODE", "\u5168\u91cf"),
    INCREMENTAL_MODE("INCREMENTAL_MODE", "\u589e\u91cf");

    private final String code;
    private final String title;

    private TransferMode(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

