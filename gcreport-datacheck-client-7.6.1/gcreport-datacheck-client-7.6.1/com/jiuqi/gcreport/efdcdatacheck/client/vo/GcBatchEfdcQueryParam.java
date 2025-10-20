/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

public class GcBatchEfdcQueryParam {
    private String asynTaskID;
    private String orgId;
    private int pageNum;
    private int pageSize = -1;

    public String getAsynTaskID() {
        return this.asynTaskID;
    }

    public void setAsynTaskID(String asynTaskID) {
        this.asynTaskID = asynTaskID;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
}

