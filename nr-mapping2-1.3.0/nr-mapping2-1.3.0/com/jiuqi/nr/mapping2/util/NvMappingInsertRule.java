/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.util;

public enum NvMappingInsertRule {
    FULL_INSERT("FULL", "\u5168\u91cf\u6a21\u5f0f");

    private final String title;
    private final String type;

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    private NvMappingInsertRule(String type, String title) {
        this.title = title;
        this.type = type;
    }
}

