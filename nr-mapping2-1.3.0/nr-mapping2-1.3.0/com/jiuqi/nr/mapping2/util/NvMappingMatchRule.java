/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.util;

public enum NvMappingMatchRule {
    MATCH_BY_CODE("CODE", "\u6309\u4ee3\u7801");

    private final String title;
    private final String type;

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    private NvMappingMatchRule(String type, String title) {
        this.title = title;
        this.type = type;
    }
}

