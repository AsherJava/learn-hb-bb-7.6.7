/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.snapshot.deploy;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class ColumnInfo {
    private String code;
    private String name;
    private ColumnModelType type;
    private int size;
    private String referFieldID;
    private boolean keyField;

    public ColumnInfo(String code, String name, ColumnModelType type, int size, String referFieldID, boolean isKeyField) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.size = size;
        this.referFieldID = referFieldID;
        this.keyField = isKeyField;
    }

    public ColumnInfo(String code, String name, ColumnModelType type, int size, String referFieldID) {
        this(code, name, type, size, referFieldID, false);
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

    public String getReferFieldID() {
        return this.referFieldID;
    }

    public boolean isKeyField() {
        return this.keyField;
    }
}

