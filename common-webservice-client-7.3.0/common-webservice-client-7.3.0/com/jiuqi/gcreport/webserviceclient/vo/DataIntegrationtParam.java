/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.webserviceclient.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DataIntegrationtParam
implements Serializable {
    private String taskKey;
    private String formSchemeKey;
    private List<String> orgCodeList;
    private String periodString;
    private Map<String, DimensionValue> dimensionSet;
    private String etlTaskName;
    private List<String> formKeyList;
    private String orgType;
    private List<Map<String, String>> fromKeyListMap;
    private List<String> executeMessages;
    private boolean status;
    private String libraryDate;
    private int offset;

    public List<Map<String, String>> getFromKeyListMap() {
        return this.fromKeyListMap;
    }

    public void setFromKeyListMap(List<Map<String, String>> fromKeyListMap) {
        this.fromKeyListMap = fromKeyListMap;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLibraryDate() {
        return this.libraryDate;
    }

    public void setLibraryDate(String libraryDate) {
        this.libraryDate = libraryDate;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<String> getExecuteMessages() {
        return this.executeMessages;
    }

    public void setExecuteMessages(List<String> executeMessages) {
        this.executeMessages = executeMessages;
    }

    public List<String> getFormKeyList() {
        return this.formKeyList;
    }

    public void setFormKeyList(List<String> formKeyList) {
        this.formKeyList = formKeyList;
    }

    public String getTaskKey() {
        return this.taskKey;
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

    public List<String> getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getPeriodString() {
        return this.periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getEtlTaskName() {
        return this.etlTaskName;
    }

    public void setEtlTaskName(String etlTaskName) {
        this.etlTaskName = etlTaskName;
    }
}

