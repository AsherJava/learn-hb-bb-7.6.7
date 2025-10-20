/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.dto;

public class TempTableParamDTO {
    private String sn;
    private boolean enableTempTable;
    private int inParamValueMaxCount;

    public TempTableParamDTO(String sn, boolean enableTempTable, int inParamValueMaxCount) {
        this.sn = sn;
        this.enableTempTable = enableTempTable;
        this.inParamValueMaxCount = inParamValueMaxCount;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public boolean isEnableTempTable() {
        return this.enableTempTable;
    }

    public void setEnableTempTable(boolean enableTempTable) {
        this.enableTempTable = enableTempTable;
    }

    public int getInParamValueMaxCount() {
        return this.inParamValueMaxCount;
    }

    public void setInParamValueMaxCount(int inParamValueMaxCount) {
        this.inParamValueMaxCount = inParamValueMaxCount;
    }
}

