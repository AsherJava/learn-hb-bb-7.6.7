/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import java.util.Map;

public class CrossFormSettingVO {
    private String globalDim;
    private String globalDimValue;
    private String rowDim;
    private String colDim;
    private Map<String, String> rowDimSetting;
    private Map<String, String> colDimSetting;
    private Map<String, Object> rowDimValueSetting;
    private Map<String, Object> colDimValueSetting;

    public String getGlobalDim() {
        return this.globalDim;
    }

    public void setGlobalDim(String globalDim) {
        this.globalDim = globalDim;
    }

    public String getGlobalDimValue() {
        return this.globalDimValue;
    }

    public void setGlobalDimValue(String globalDimValue) {
        this.globalDimValue = globalDimValue;
    }

    public String getRowDim() {
        return this.rowDim;
    }

    public void setRowDim(String rowDim) {
        this.rowDim = rowDim;
    }

    public String getColDim() {
        return this.colDim;
    }

    public void setColDim(String colDim) {
        this.colDim = colDim;
    }

    public Map<String, String> getRowDimSetting() {
        return this.rowDimSetting;
    }

    public void setRowDimSetting(Map<String, String> rowDimSetting) {
        this.rowDimSetting = rowDimSetting;
    }

    public Map<String, String> getColDimSetting() {
        return this.colDimSetting;
    }

    public void setColDimSetting(Map<String, String> colDimSetting) {
        this.colDimSetting = colDimSetting;
    }

    public Map<String, Object> getRowDimValueSetting() {
        return this.rowDimValueSetting;
    }

    public void setRowDimValueSetting(Map<String, Object> rowDimValueSetting) {
        this.rowDimValueSetting = rowDimValueSetting;
    }

    public Map<String, Object> getColDimValueSetting() {
        return this.colDimValueSetting;
    }

    public void setColDimValueSetting(Map<String, Object> colDimValueSetting) {
        this.colDimValueSetting = colDimValueSetting;
    }
}

