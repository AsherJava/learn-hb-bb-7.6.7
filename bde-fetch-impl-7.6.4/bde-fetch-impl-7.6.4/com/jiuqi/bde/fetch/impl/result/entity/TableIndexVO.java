/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 */
package com.jiuqi.bde.fetch.impl.result.entity;

import com.jiuqi.nvwa.definition.common.IndexModelType;
import java.util.List;

public class TableIndexVO {
    private List<String> fields;
    private IndexModelType indexType;
    private String indexName;

    public List<String> getFields() {
        return this.fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public IndexModelType getIndexType() {
        return this.indexType;
    }

    public void setIndexType(IndexModelType indexType) {
        this.indexType = indexType;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}

