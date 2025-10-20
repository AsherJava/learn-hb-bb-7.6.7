/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import java.util.HashMap;
import java.util.Map;

public enum PaperSize {
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

    private int value;
    private String title;
    private static final Map<Integer, PaperSize> valueFindr;
    private static final Map<String, PaperSize> nameFindr;

    private PaperSize(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static PaperSize valueOf(int value) {
        return valueFindr.get(value);
    }

    public static PaperSize parse(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return nameFindr.get(s.toUpperCase());
    }

    public static String[] titles() {
        PaperSize[] types = PaperSize.values();
        String[] titles = new String[types.length];
        for (int i = 0; i < types.length; ++i) {
            titles[i] = types[i].title;
        }
        return titles;
    }

    static {
        valueFindr = new HashMap<Integer, PaperSize>();
        nameFindr = new HashMap<String, PaperSize>();
        for (PaperSize ps : PaperSize.values()) {
            valueFindr.put(ps.value(), ps);
            nameFindr.put(ps.name().toUpperCase(), ps);
            nameFindr.put(ps.title().toUpperCase(), ps);
        }
    }
}

