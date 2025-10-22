/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.exe.SumCell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumExeInfo {
    private List<SumCell> cells = new ArrayList<SumCell>();
    private DimensionValueSet srcDimension;
    private DimensionValueSet destDimension;
    private String filter;
    private FieldDefine orderField;
    private Map<String, FieldDefine> fieldMap = new HashMap<String, FieldDefine>();

    public DimensionValueSet getSrcDimension() {
        return this.srcDimension;
    }

    public DimensionValueSet getDestDimension() {
        return this.destDimension;
    }

    public FieldDefine getOrderField() {
        return this.orderField;
    }

    public void setSrcDimension(DimensionValueSet srcDimension) {
        this.srcDimension = srcDimension;
    }

    public void setDestDimension(DimensionValueSet destDimension) {
        this.destDimension = destDimension;
    }

    public void setOrderField(FieldDefine orderField) {
        this.orderField = orderField;
    }

    public Map<String, FieldDefine> getFieldMap() {
        return this.fieldMap;
    }

    public List<SumCell> getCells() {
        return this.cells;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("sumCells:\n");
        for (SumCell cell : this.cells) {
            buff.append(cell).append("\n");
        }
        buff.append("srcDimension:").append(this.srcDimension).append("\n");
        buff.append("destDimension:").append(this.destDimension).append("\n");
        if (this.filter != null) {
            buff.append("filter:").append(this.filter);
        }
        return buff.toString();
    }
}

