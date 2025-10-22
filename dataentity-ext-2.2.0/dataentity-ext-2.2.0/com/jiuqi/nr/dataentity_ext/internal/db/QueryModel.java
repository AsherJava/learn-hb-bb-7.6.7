/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.internal.db;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.PageInfo;
import java.util.List;

public class QueryModel {
    private final String tableName;
    private List<String> keys;
    private List<String> parents;
    private List<EntityDataType> types;
    private Boolean leaf;
    private List<String> keyWords;
    private PageInfo pageInfo;
    private boolean sort;

    public QueryModel(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public List<EntityDataType> getTypes() {
        return this.types;
    }

    public void setTypes(List<EntityDataType> types) {
        this.types = types;
    }

    public List<String> getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public boolean isSort() {
        return this.sort;
    }

    public void setSort(boolean sort) {
        this.sort = sort;
    }

    public Boolean getLeaf() {
        return this.leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
}

