/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

public class ClbrSchemeDTO {
    private String id;
    private String parentId;
    private Boolean leafFlag;
    private String title;
    private String schemeDesc;
    private String clbrTypes;
    private String relations;
    private String oppRelations;
    private String oppClbrTypes;
    private String flowControlType;
    private String vchrControlType;

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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public String getSchemeDesc() {
        return this.schemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        this.schemeDesc = schemeDesc;
    }
}

