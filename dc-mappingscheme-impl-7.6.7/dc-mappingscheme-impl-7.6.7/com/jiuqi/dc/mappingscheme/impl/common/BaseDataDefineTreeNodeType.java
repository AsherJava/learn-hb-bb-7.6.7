/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.common;

public enum BaseDataDefineTreeNodeType {
    ROOT("-"),
    SCHEME("scheme"),
    ITEM("item");

    private String code;

    private BaseDataDefineTreeNodeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

