/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define;

import com.jiuqi.nr.data.engine.summary.define.RelationInfo;
import java.util.HashMap;
import java.util.Map;

public class DimRelationInfo
extends RelationInfo {
    private String dimName;
    private boolean supportZipperVersion = false;
    private String startDateFieldName;
    private String endDateFieldName;
    private Map<String, DimRelationInfo> relationInfos = new HashMap<String, DimRelationInfo>();

    public DimRelationInfo getRelationInfo(String refTableName) {
        return this.relationInfos.get(refTableName);
    }

    public void addRelationInfo(DimRelationInfo relationInfo) {
        this.relationInfos.put(relationInfo.getDestTableName(), relationInfo);
    }

    public boolean hasRelation() {
        return this.relationInfos.size() > 0;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public boolean isSupportZipperVersion() {
        return this.supportZipperVersion;
    }

    public void setSupportZipperVersion(boolean supportZipperVersion) {
        this.supportZipperVersion = supportZipperVersion;
    }

    public String getStartDateFieldName() {
        return this.startDateFieldName;
    }

    public void setStartDateFieldName(String startDateFieldName) {
        this.startDateFieldName = startDateFieldName;
    }

    public String getEndDateFieldName() {
        return this.endDateFieldName;
    }

    public void setEndDateFieldName(String endDateFieldName) {
        this.endDateFieldName = endDateFieldName;
    }

    public Map<String, DimRelationInfo> getRelationInfos() {
        return this.relationInfos;
    }

    public void setRelationInfos(Map<String, DimRelationInfo> relationInfos) {
        this.relationInfos = relationInfos;
    }
}

