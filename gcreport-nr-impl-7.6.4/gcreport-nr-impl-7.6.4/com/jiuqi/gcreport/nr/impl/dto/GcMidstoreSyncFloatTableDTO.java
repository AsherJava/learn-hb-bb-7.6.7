/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.impl.dto;

import java.util.List;

public class GcMidstoreSyncFloatTableDTO {
    private String dataTime;
    private String mdCode;
    private List<String> zbNames;
    private List<Object> zbValues;

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public List<String> getZbNames() {
        return this.zbNames;
    }

    public void setZbNames(List<String> zbNames) {
        this.zbNames = zbNames;
    }

    public List<Object> getZbValues() {
        return this.zbValues;
    }

    public void setZbValues(List<Object> zbValues) {
        this.zbValues = zbValues;
    }
}

