/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.distributeds;

import java.util.ArrayList;
import java.util.List;

public final class DistributeDsDimValue {
    private String dimName;
    private String attrName;
    private List<String> values = new ArrayList<String>();

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getAttrName() {
        return this.attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public List<String> getValues() {
        return this.values;
    }

    public boolean equals(Object obj) {
        DistributeDsDimValue dimValue;
        if (super.equals(obj)) {
            return true;
        }
        return obj instanceof DistributeDsDimValue && (dimValue = (DistributeDsDimValue)obj).getDimName().equals(this.dimName) && dimValue.getAttrName().equals(this.attrName);
    }

    public int hashCode() {
        return (this.dimName + this.attrName).hashCode();
    }

    public String toString() {
        return this.dimName + "." + this.attrName;
    }
}

