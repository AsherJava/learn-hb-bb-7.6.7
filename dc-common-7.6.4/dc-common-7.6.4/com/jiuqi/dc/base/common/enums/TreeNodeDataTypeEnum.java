/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum TreeNodeDataTypeEnum {
    DATATYPE_ROOT("root"),
    DATATYPE_FOLDER("folder"),
    DATATYPE_LEAF("leaf");

    private String value;

    private TreeNodeDataTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

