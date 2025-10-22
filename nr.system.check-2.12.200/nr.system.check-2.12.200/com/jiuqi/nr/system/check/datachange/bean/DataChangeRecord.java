/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.bean;

import java.io.Serializable;
import java.util.Date;

public class DataChangeRecord
implements Serializable {
    private String id;
    private String oldUnitCode;
    private String newUnitCode;
    private String period;
    private String recordType;
    private Date changeTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldUnitCode() {
        return this.oldUnitCode;
    }

    public void setOldUnitCode(String oldUnitCode) {
        this.oldUnitCode = oldUnitCode;
    }

    public String getNewUnitCode() {
        return this.newUnitCode;
    }

    public void setNewUnitCode(String newUnitCode) {
        this.newUnitCode = newUnitCode;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRecordType() {
        return this.recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Date getChangeTime() {
        return this.changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DataChangeRecord) {
            String oldUnit = ((DataChangeRecord)obj).getOldUnitCode();
            String period = ((DataChangeRecord)obj).getPeriod();
            return oldUnit.equals(this.oldUnitCode) && period.equals(this.period);
        }
        return false;
    }

    public int hashCode() {
        return this.oldUnitCode.hashCode() + this.period.hashCode();
    }
}

