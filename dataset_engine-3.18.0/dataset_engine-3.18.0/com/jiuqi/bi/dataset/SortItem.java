/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

public class SortItem {
    public static final int TYPE_ASC = 1;
    public static final int TYPE_DESC = -1;
    private String fieldName;
    private int sortType = 1;

    public SortItem() {
    }

    public SortItem(String fieldName, int sortType) {
        this.fieldName = fieldName;
        this.sortType = sortType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public int getSortType() {
        return this.sortType;
    }

    public boolean equals(Object obj) {
        if (obj instanceof SortItem) {
            SortItem item = (SortItem)obj;
            return this.fieldName.equals(item.fieldName) && this.sortType == item.sortType;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return this.fieldName.hashCode() + this.sortType;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[").append(this.fieldName).append(",");
        if (this.sortType == 1) {
            buf.append("ASC");
        } else if (this.sortType == -1) {
            buf.append("DESC");
        } else {
            buf.append("UNKNOWN");
        }
        buf.append("]");
        return buf.toString();
    }
}

