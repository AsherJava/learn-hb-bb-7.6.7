/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.vo;

public class ClbrReceiveSettingCondition {
    private String oppClbrType;
    private String oppRelation;
    private Integer pageNum;
    private Integer PageSize;

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.PageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.PageSize = pageSize;
    }

    public String getOppClbrType() {
        return this.oppClbrType;
    }

    public void setOppClbrType(String oppClbrType) {
        this.oppClbrType = oppClbrType;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }
}

