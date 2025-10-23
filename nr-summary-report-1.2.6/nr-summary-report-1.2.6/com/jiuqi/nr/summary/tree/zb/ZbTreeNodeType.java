/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.zb;

public enum ZbTreeNodeType {
    DATASCHEME(0),
    TABLE(1),
    DETAIL(2),
    ZB(3),
    FIELD(4);

    private int code;

    private ZbTreeNodeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static ZbTreeNodeType valueOf(int code) {
        for (ZbTreeNodeType type : ZbTreeNodeType.values()) {
            if (type.getCode() != code) continue;
            return type;
        }
        return null;
    }
}

