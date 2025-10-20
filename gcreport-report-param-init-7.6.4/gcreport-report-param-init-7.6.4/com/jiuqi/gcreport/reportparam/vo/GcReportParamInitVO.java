/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportparam.vo;

public class GcReportParamInitVO {
    private String name;
    private String type;
    private String description;
    private boolean initFlag;
    private String relatedMergeSystem;
    private String relatedOrgTypes;

    public GcReportParamInitVO() {
    }

    public GcReportParamInitVO(String name, String type, String description, boolean initFlag) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.initFlag = initFlag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInitFlag() {
        return this.initFlag;
    }

    public void setInitFlag(boolean initFlag) {
        this.initFlag = initFlag;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelatedMergeSystem() {
        return this.relatedMergeSystem;
    }

    public void setRelatedMergeSystem(String relatedMergeSystem) {
        this.relatedMergeSystem = relatedMergeSystem;
    }

    public String getRelatedOrgTypes() {
        return this.relatedOrgTypes;
    }

    public void setRelatedOrgTypes(String relatedOrgTypes) {
        this.relatedOrgTypes = relatedOrgTypes;
    }
}

