/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 */
package com.jiuqi.nr.data.access.model;

import com.jiuqi.nvwa.definition.common.IndexModelType;

public class IndexInfo {
    private String indexName;
    private int[] indexColumns;
    private IndexModelType indexModelType;
    private String description;

    public IndexInfo(String indexName, int[] indexColumns, IndexModelType indexModelType) {
        this.indexName = indexName;
        this.indexColumns = indexColumns;
        this.indexModelType = indexModelType;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public int[] getIndexColumns() {
        return this.indexColumns;
    }

    public void setIndexColumns(int[] indexColumns) {
        this.indexColumns = indexColumns;
    }

    public IndexModelType getIndexModelType() {
        return this.indexModelType;
    }

    public void setIndexModelType(IndexModelType indexModelType) {
        this.indexModelType = indexModelType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

