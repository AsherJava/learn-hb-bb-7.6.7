/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.bean;

public class UnitInfo {
    private String entityId;
    private String period;
    private String unit;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean equals(Object o) {
        if (o instanceof UnitInfo) {
            UnitInfo unitInfo = (UnitInfo)o;
            return this.entityId.equals(unitInfo.entityId) && this.period.equals(unitInfo.period) && this.unit.equals(unitInfo.unit);
        }
        return false;
    }

    public int hashCode() {
        return this.entityId.hashCode() + this.period.hashCode() + this.unit.hashCode();
    }
}

