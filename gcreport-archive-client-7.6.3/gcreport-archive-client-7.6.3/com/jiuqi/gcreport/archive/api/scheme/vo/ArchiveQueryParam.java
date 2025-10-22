/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.api.scheme.vo;

import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;

public class ArchiveQueryParam {
    private int pageNum;
    private int pageSize;
    private ArchiveContext queryConditions;

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

    public ArchiveContext getQueryConditions() {
        return this.queryConditions;
    }

    public void setQueryConditions(ArchiveContext queryConditions) {
        this.queryConditions = queryConditions;
    }
}

