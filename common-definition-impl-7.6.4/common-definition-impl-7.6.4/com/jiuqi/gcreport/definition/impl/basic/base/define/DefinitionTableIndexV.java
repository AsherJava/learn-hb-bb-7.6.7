/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 */
package com.jiuqi.gcreport.definition.impl.basic.base.define;

import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import java.util.Arrays;

public class DefinitionTableIndexV {
    private String key;
    private String title;
    private String[] columnIds;
    private String[] columnsFields;
    private IndexModelType indexType;

    public DefinitionTableIndexV() {
    }

    public DefinitionTableIndexV(DBIndex index) {
        this.setColumnsFields(index.columnsFields());
        this.setIndexType(IndexModelType.forValue((int)index.type().getValue()));
        this.setTitle(index.name());
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getColumnsFields() {
        return this.columnsFields;
    }

    public void setColumnsFields(String[] columnsFields) {
        this.columnsFields = columnsFields;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getColumnIds() {
        return this.columnIds;
    }

    public void setColumnsIds(String[] columnsFieldKeys) {
        this.columnIds = columnsFieldKeys;
    }

    public IndexModelType getIndexType() {
        return this.indexType;
    }

    public void setIndexType(IndexModelType indexType) {
        this.indexType = indexType;
    }

    public String toString() {
        return "DefinitionTableIndexV{key='" + this.key + '\'' + ", title='" + this.title + '\'' + ", columnsFieldKeys=" + Arrays.toString(this.columnIds) + ", columnsFields=" + Arrays.toString(this.columnsFields) + ", indexType=" + this.indexType + '}';
    }
}

