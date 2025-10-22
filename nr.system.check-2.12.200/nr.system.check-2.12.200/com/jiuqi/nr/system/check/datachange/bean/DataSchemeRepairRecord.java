/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.bean;

import java.io.Serializable;
import java.util.Date;

public class DataSchemeRepairRecord
implements Serializable {
    private String dataSchemeKey;
    private Date startTime;
    private Date endTime;
    private String repairType;
    private boolean hasDataExport = false;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRepairType() {
        return this.repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public boolean isHasDataExport() {
        return this.hasDataExport;
    }

    public void setHasDataExport(boolean hasDataExport) {
        this.hasDataExport = hasDataExport;
    }
}

