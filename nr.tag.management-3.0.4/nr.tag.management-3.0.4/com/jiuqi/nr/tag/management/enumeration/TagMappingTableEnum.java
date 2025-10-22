/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.enumeration;

public enum TagMappingTableEnum {
    TM_TAGKEY("TM_TAGKEY"),
    TM_ENTITYDATA("TM_ENTITYDATA"),
    TM_ORDER("TM_ORDER");

    public final String column;

    private TagMappingTableEnum(String column) {
        this.column = column;
    }
}

