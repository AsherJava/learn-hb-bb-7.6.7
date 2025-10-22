/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.sb.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RowDimValue {
    private String sbId;
    private String mdCode;
    private final List<Object> dim = new ArrayList<Object>();
    private List<Object> values;
    private List<Object> oldValues;
    private int opt = 0;

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getSbId() {
        return this.sbId;
    }

    public void setSbId(String sbId) {
        this.sbId = sbId;
    }

    public List<Object> getDim() {
        return this.dim;
    }

    public List<Object> getValues() {
        return this.values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public List<Object> getOldValues() {
        return this.oldValues;
    }

    public void setOldValues(List<Object> oldValues) {
        this.oldValues = oldValues;
    }

    public void addValue(Object value) {
        this.values.add(value);
    }

    public void addValues(Object[] values) {
        this.values.addAll(Arrays.asList(values));
    }

    public void addOldValue(Object value) {
        this.oldValues.add(value);
    }

    public void addOldValues(Object[] values) {
        this.oldValues.addAll(Arrays.asList(values));
    }

    public int getOpt() {
        return this.opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RowDimValue dimValue = (RowDimValue)o;
        if (!Objects.equals(this.mdCode, dimValue.mdCode)) {
            return false;
        }
        return this.dim.equals(dimValue.dim);
    }

    public int hashCode() {
        int result = this.mdCode != null ? this.mdCode.hashCode() : 0;
        result = 31 * result + this.dim.hashCode();
        return result;
    }

    public String toString() {
        return "RowDimValue{sbId='" + this.sbId + '\'' + ", mdCode='" + this.mdCode + '\'' + ", dim=" + this.dim + ", values=" + this.values + ", oldValues=" + this.oldValues + ", opt=" + this.opt + '}';
    }
}

