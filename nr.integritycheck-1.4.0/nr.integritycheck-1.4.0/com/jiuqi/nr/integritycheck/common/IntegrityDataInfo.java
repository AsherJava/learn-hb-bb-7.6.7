/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.integritycheck.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.common.params.PagerInfo;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class IntegrityDataInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String masterDimName;
    private PagerInfo pagerInfo;
    private int lackCount;
    private List<List<String>> rowDatas;
    private List<String> fmKeyList;
    private LinkedHashMap<String, String> dwCodeKey;
    private LinkedHashMap<String, String> formCodeKey;
    private List<String> headerList;
    private Map<String, Integer> formLackMap;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getMasterDimName() {
        return this.masterDimName;
    }

    public void setMasterDimName(String masterDimName) {
        this.masterDimName = masterDimName;
    }

    public List<String> getFmKeyList() {
        return this.fmKeyList;
    }

    public void setFmKeyList(List<String> fmKeyList) {
        this.fmKeyList = fmKeyList;
    }

    public void setHeaderList(List<String> headerList) {
        this.headerList = headerList;
    }

    public List<String> getHeaderList() {
        return this.headerList;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public LinkedHashMap<String, String> getDwCodeKey() {
        return this.dwCodeKey;
    }

    public void setDwCodeKey(LinkedHashMap<String, String> dwCodeKey) {
        this.dwCodeKey = dwCodeKey;
    }

    public LinkedHashMap<String, String> getFormCodeKey() {
        return this.formCodeKey;
    }

    public void setFormCodeKey(LinkedHashMap<String, String> formCodeKey) {
        this.formCodeKey = formCodeKey;
    }

    public int getLackCount() {
        return this.lackCount;
    }

    public void setLackCount(int lackCount) {
        this.lackCount = lackCount;
    }

    public Map<String, Integer> getFormLackMap() {
        return this.formLackMap;
    }

    public void setFormLackMap(Map<String, Integer> formLackMap) {
        this.formLackMap = formLackMap;
    }

    public List<List<String>> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<List<String>> rowDatas) {
        this.rowDatas = rowDatas;
    }
}

