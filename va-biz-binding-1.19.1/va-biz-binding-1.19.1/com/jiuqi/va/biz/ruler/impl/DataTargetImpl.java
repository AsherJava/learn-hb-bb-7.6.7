/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.biz.intf.data.DataTarget;
import com.jiuqi.va.biz.intf.data.DataTargetType;
import java.util.UUID;

public class DataTargetImpl
implements DataTarget {
    private DataTargetType targetType;
    private String tableName;
    private String fieldName;
    private int rowIndex;
    private UUID rowID;

    @Override
    public DataTargetType getTargetType() {
        return this.targetType;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setTargetType(DataTargetType targetType) {
        this.targetType = targetType;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Deprecated
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    public UUID getRowID() {
        return this.rowID;
    }

    public void setRowID(UUID rowID) {
        this.rowID = rowID;
    }
}

