/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.enumeration;

public enum TagDefineTableEnum {
    TD_KEY("TD_KEY"),
    TD_OWNER("TD_OWNER"),
    TD_ENTITY("TD_ENTITY"),
    TD_TITLE("TD_TITLE"),
    TD_ICON("TD_ICON"),
    TD_CATEGORY("TD_CATEGORY"),
    TD_FORMULA("TD_FORMULA"),
    TD_SHARED("TD_SHARED"),
    TD_ORDER("TD_ORDER"),
    TD_DESC("TD_DESC"),
    TD_RANGE_MODIFY("TD_RANGE_MODIFY");

    public final String column;

    private TagDefineTableEnum(String column) {
        this.column = column;
    }
}

