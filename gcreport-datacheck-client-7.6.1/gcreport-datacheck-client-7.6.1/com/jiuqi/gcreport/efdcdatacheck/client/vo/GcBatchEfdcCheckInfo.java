/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GcBatchEfdcCheckInfo
implements Serializable {
    private String taskKey;
    private String formSchemeKey;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private boolean dbTask = true;
    private Boolean includeUncharged;
    private Map<String, Set<String>> reportZbDataMap;
    private Set<String> orgIds;
    private Set<String> orignOrgIds;
    private String periodTitle;
    private boolean groupByReport;
    private String userName;
    private List<String> formKeys = new ArrayList<String>();
    private String fileName;

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public boolean isDbTask() {
        return this.dbTask;
    }

    public void setDbTask(boolean dbTask) {
        this.dbTask = dbTask;
    }

    public Map<String, Set<String>> getReportZbDataMap() {
        return this.reportZbDataMap;
    }

    public void setReportZbDataMap(Map<String, Set<String>> reportZbDataMap) {
        this.reportZbDataMap = reportZbDataMap;
    }

    public Set<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(Set<String> orgIds) {
        this.orgIds = orgIds;
    }

    public boolean isGroupByReport() {
        return this.groupByReport;
    }

    public void setGroupByReport(boolean groupByReport) {
        this.groupByReport = groupByReport;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public Set<String> getOrignOrgIds() {
        return this.orignOrgIds;
    }

    public void setOrignOrgIds(Set<String> orignOrgIds) {
        this.orignOrgIds = orignOrgIds;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }
}

