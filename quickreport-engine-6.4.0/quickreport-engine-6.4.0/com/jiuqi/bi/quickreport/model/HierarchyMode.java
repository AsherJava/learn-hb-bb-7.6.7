/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.model;

public enum HierarchyMode {
    LIST,
    INDENTED,
    TIERED;

    private static final String LEGACY_FOLDING = "FOLDING";

    public static HierarchyMode parse(String value) {
        if (LEGACY_FOLDING.equals(value)) {
            return TIERED;
        }
        return HierarchyMode.valueOf(value);
    }
}

