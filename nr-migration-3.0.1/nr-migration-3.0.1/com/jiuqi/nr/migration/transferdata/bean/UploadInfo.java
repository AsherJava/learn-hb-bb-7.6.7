/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.migration.transferdata.bean;

import org.json.JSONObject;

public class UploadInfo {
    private String solutionName;
    private String periodValue;
    private String tableName;
    private int unitCount;

    public String getUploadInfo() {
        JSONObject json = new JSONObject();
        json.put("\u4efb\u52a1\uff1a", (Object)this.solutionName);
        json.put("\u65f6\u671f\uff1a", (Object)this.periodValue);
        json.put("\u4e3b\u7ef4\u5ea6\u6807\u8bc6\uff1a", (Object)this.tableName);
        json.put("\u5355\u4f4d\u6570\u91cf\uff1a", this.unitCount);
        return json.toString();
    }

    public String getSolutionName() {
        return this.solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getUnitCount() {
        return this.unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }
}

