/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.report.tree;

public class UnitRow {
    private String unitKey;
    private String unitParent;
    private boolean detail;

    public UnitRow(String unitKey, String unitParent, boolean detail) {
        this.unitKey = unitKey;
        this.unitParent = unitParent;
        this.detail = detail;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public String getUnitParent() {
        return this.unitParent;
    }

    public boolean isDetail() {
        return this.detail;
    }

    public String toString() {
        return "UnitRow [unitKey=" + this.unitKey + ", unitParent=" + this.unitParent + ", detail=" + this.detail + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.detail ? 1231 : 1237);
        result = 31 * result + (this.unitKey == null ? 0 : this.unitKey.hashCode());
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
        UnitRow other = (UnitRow)obj;
        if (this.detail != other.detail) {
            return false;
        }
        return !(this.unitKey == null ? other.unitKey != null : !this.unitKey.equals(other.unitKey));
    }
}

