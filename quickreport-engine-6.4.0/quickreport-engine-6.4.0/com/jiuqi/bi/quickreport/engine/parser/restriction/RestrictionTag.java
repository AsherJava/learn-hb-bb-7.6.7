/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

public class RestrictionTag {
    public static final String ALL = "ALL";
    public static final String MB = "MB";
    public static final String NEXT = "NEXT";
    public static final String PREV = "PREV";

    private RestrictionTag() {
    }

    public static boolean isTag(String tag) {
        return ALL.equalsIgnoreCase(tag) || MB.equalsIgnoreCase(tag) || NEXT.equalsIgnoreCase(tag) || PREV.equalsIgnoreCase(tag);
    }
}

