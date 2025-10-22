/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.enumcheck.deploy;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class ColumnInfo {
    private String code;
    private String name;
    private ColumnModelType type;
    private int size;
    private String referFieldId;
    private boolean keyField;

    public ColumnInfo(String code, String name, ColumnModelType type, int size, String referFieldId, boolean isKeyField) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.size = size;
        this.referFieldId = referFieldId;
        this.keyField = isKeyField;
    }

    public ColumnInfo(String code, String name, ColumnModelType type, int size, String referFieldId) {
        this(code, name, type, size, referFieldId, false);
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public ColumnModelType getType() {
        return this.type;
    }

    public int getSize() {
        return this.size;
    }

    public String getReferFieldId() {
        return this.referFieldId;
    }

    public boolean isKeyField() {
        return this.keyField;
    }
}

