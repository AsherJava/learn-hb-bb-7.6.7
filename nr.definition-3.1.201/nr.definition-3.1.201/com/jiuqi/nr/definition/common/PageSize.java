/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.definition.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum PageSize {
    LETTER_PAPER(1, "\u4fe1\u7eb8"),
    TABLOID_PAPER(3, "Tabloid"),
    LEGAL_PAPER(5, "\u6cd5\u5f8b\u4e13\u7528\u7eb8"),
    STATEMENT_PAPER(6, "Statement"),
    EXECUTIVE_PAPER(7, "Executive"),
    A3_PAPER(8, "A3"),
    A4_PAPER(9, "A4"),
    A5_PAPER(11, "A5"),
    B4_PAPER(12, "B4"),
    B5_PAPER(13, "B5");

    private final int value;
    private final String title;
    private static final Map<Integer, PageSize> VALUE_FINDER;
    private static final Map<String, PageSize> TITLE_FINDER;

    private PageSize(int value, String title) {
        this.value = value;
        this.title = title;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    @JsonCreator
    public static PageSize valueOf(int value) {
        return VALUE_FINDER.get(value);
    }

    public static PageSize parse(String s) {
        return TITLE_FINDER.get(s.toUpperCase());
    }

    public static String[] titles() {
        PageSize[] types = PageSize.values();
        String[] titles = new String[types.length];
        for (int i = 0; i < types.length; ++i) {
            titles[i] = types[i].title;
        }
        return titles;
    }

    static {
        VALUE_FINDER = new HashMap<Integer, PageSize>();
        TITLE_FINDER = new HashMap<String, PageSize>();
        for (PageSize ps : PageSize.values()) {
            VALUE_FINDER.put(ps.getValue(), ps);
            TITLE_FINDER.put(ps.name().toUpperCase(), ps);
            TITLE_FINDER.put(ps.getTitle().toUpperCase(), ps);
        }
    }
}

