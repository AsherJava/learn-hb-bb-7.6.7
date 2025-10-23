/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.datascheme.api.DataDimension;

public class DimFieldInfo {
    private String code;
    private String title;
    private DataDimension matchedDim;

    public DimFieldInfo(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataDimension getMatchedDim() {
        return this.matchedDim;
    }

    public void setMatchedDim(DataDimension matchedDim) {
        this.matchedDim = matchedDim;
    }

    public String toString() {
        return "DimFieldInfo [code=" + this.code + ", title=" + this.title + ", matchedDim=" + (this.matchedDim == null ? "" : this.matchedDim.getDimKey()) + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.code == null ? 0 : this.code.hashCode());
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
        DimFieldInfo other = (DimFieldInfo)obj;
        return !(this.code == null ? other.code != null : !this.code.equals(other.code));
    }
}

