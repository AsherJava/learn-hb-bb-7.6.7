/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.dto;

import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentity_ext.dto.PageInfo;
import java.util.List;

public class QueryParam {
    private final String resourceId;
    private List<String> keys;
    private List<EntityDataType> types;
    private List<String> keyWords;
    private PageInfo pageInfo;
    private boolean sort;

    public QueryParam(String resourceId) {
        this.resourceId = resourceId;
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

    public String getResourceId() {
        return this.resourceId;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }
}

