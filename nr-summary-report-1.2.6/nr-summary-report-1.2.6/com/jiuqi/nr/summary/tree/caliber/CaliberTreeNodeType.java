/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.caliber;

public enum CaliberTreeNodeType {
    MASTERDIM(1),
    MASTERDIMFIELD(2),
    INNERDIM(9),
    CALIBER(10),
    MASTERDIM_ITEM(5),
    MASTERDIM_FIELD_ITEM(6),
    INNERDIM_ITEM(7),
    CALIBER_ITEM(8);

    private int code;

    private CaliberTreeNodeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static CaliberTreeNodeType valueOf(int code) {
        for (CaliberTreeNodeType type : CaliberTreeNodeType.values()) {
            if (type.code != code) continue;
            return type;
        }
        return null;
    }
}

