/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class DesignFieldDefineVO {
    private String key;
    private String label;
    private ColumnModelType type;
    private boolean defaultShow;
    private String dictTableName;

    public ColumnModelType getType() {
        return this.type;
    }

    public void setType(ColumnModelType type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDefaultShow() {
        return this.defaultShow;
    }

    public void setDefaultShow(boolean defaultShow) {
        this.defaultShow = defaultShow;
    }

    public String getDictTableName() {
        return this.dictTableName;
    }

    public void setDictTableName(String dictTableName) {
        this.dictTableName = dictTableName;
    }
}

