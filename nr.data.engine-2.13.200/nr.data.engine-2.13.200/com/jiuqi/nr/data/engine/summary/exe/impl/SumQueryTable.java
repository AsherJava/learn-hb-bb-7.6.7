/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

public class SumQueryTable {
    private String tableName;
    private String periodOffSet;

    public SumQueryTable(String tableName, String periodOffSet) {
        this.tableName = tableName;
        this.periodOffSet = periodOffSet;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPeriodOffSet() {
        return this.periodOffSet;
    }

    public void setPeriodOffSet(String periodOffSet) {
        this.periodOffSet = periodOffSet;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.periodOffSet == null ? 0 : this.periodOffSet.hashCode());
        result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SumQueryTable other = (SumQueryTable)obj;
        if (this.periodOffSet == null ? other.periodOffSet != null : !this.periodOffSet.equals(other.periodOffSet)) {
            return false;
        }
        return !(this.tableName == null ? other.tableName != null : !this.tableName.equals(other.tableName));
    }

    public String toString() {
        return "SumQueryTable [tableName=" + this.tableName + ", periodOffSet=" + this.periodOffSet + "]";
    }
}

