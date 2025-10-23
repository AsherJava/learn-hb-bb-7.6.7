/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.data.dto;

import java.util.List;

public class UnitParamVO {
    private String dwSchemeKey;
    private int type;
    private int pageNum;
    private int pageSize;
    private List<String> entityIds;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getDwSchemeKey() {
        return this.dwSchemeKey;
    }

    public void setDwSchemeKey(String dwSchemeKey) {
        this.dwSchemeKey = dwSchemeKey;
    }

    public List<String> getEntityIds() {
        return this.entityIds;
    }

    public void setEntityIds(List<String> entityIds) {
        this.entityIds = entityIds;
    }
}

