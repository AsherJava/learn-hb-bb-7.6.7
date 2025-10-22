/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 */
package com.jiuqi.nr.formtype.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.HashMap;
import java.util.Map;

public enum UnitNature {
    JCFHB(0, "\u57fa\u5c42\u5206\u6237\u8868", UnitNatureRule.NONE),
    JTCEB(1, "\u96c6\u56e2\u5dee\u989d\u8868", UnitNatureRule.UNIQUE),
    BZHZB(7, "\u6807\u51c6\u6c47\u603b\u8868", UnitNatureRule.UNIQUE),
    JTHZB(9, "\u96c6\u56e2\u6c47\u603b\u8868", UnitNatureRule.UNIQUE);

    private int value;
    private String title;
    private UnitNatureRule rule;
    private static final Map<Integer, UnitNature> ALL_NATURE;

    @JsonCreator
    private UnitNature(int value, String title, UnitNatureRule rule) {
        this.value = value;
        this.title = title;
        this.rule = rule;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public UnitNatureRule getRule() {
        return this.rule;
    }

    public static UnitNature valueOf(int value) {
        if (ALL_NATURE.containsKey(value)) {
            return ALL_NATURE.get(value);
        }
        return null;
    }

    static {
        ALL_NATURE = new HashMap<Integer, UnitNature>();
        for (UnitNature nature : UnitNature.values()) {
            ALL_NATURE.put(nature.getValue(), nature);
        }
    }

    @JsonFormat(shape=JsonFormat.Shape.OBJECT)
    public static enum UnitNatureRule {
        NONE(0, "\u65e0\u8981\u6c42"),
        UNIQUE(1, "\u552f\u4e00\uff0c\u6700\u591a\u9009\u4e00\u6b21");

        private int value;
        private String desc;

        @JsonCreator
        private UnitNatureRule(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return this.value;
        }

        public String getDesc() {
            return this.desc;
        }
    }
}

