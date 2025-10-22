/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.vo;

public class ClbrSchemeCondition {
    private String id;
    private String clbrTypes;
    private String relations;
    private String oppRelations;
    private String oppClbrTypes;
    private String flowControlType;
    private String vchrControlType;
    private Integer pageNum;
    private Integer pageSize;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClbrTypes() {
        return this.clbrTypes;
    }

    public void setClbrTypes(String clbrTypes) {
        this.clbrTypes = clbrTypes;
    }

    public String getRelations() {
        return this.relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getOppRelations() {
        return this.oppRelations;
    }

    public void setOppRelations(String oppRelations) {
        this.oppRelations = oppRelations;
    }

    public String getOppClbrTypes() {
        return this.oppClbrTypes;
    }

    public void setOppClbrTypes(String oppClbrTypes) {
        this.oppClbrTypes = oppClbrTypes;
    }

    public String getFlowControlType() {
        return this.flowControlType;
    }

    public void setFlowControlType(String flowControlType) {
        this.flowControlType = flowControlType;
    }

    public String getVchrControlType() {
        return this.vchrControlType;
    }

    public void setVchrControlType(String vchrControlType) {
        this.vchrControlType = vchrControlType;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

