/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.RangeValues
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.syntax.sql.RangeValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterItem {
    private String expr;
    private String fieldName;
    private List<Object> keyList;
    private RangeValues range;

    public FilterItem() {
    }

    public FilterItem(String fieldName, String expr) {
        this.fieldName = fieldName;
        this.expr = expr;
    }

    public FilterItem(String fieldName, List<Object> keyList) {
        this.fieldName = fieldName;
        this.keyList = keyList;
    }

    public FilterItem(String fieldName, RangeValues range) {
        this.fieldName = fieldName;
        this.range = range;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public String getExpr() {
        return this.expr;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setKeyList(List<Object> keyList) {
        this.keyList = keyList;
    }

    public void setKeyValue(Object value) {
        if (this.keyList == null) {
            this.keyList = new ArrayList<Object>(1);
        } else {
            this.keyList.clear();
        }
        this.keyList.add(value);
    }

    public List<Object> getKeyList() {
        return this.keyList;
    }

    public void setRange(RangeValues range) {
        this.range = range;
    }

    public RangeValues getRange() {
        return this.range;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("(").append(this.fieldName);
        if (this.expr != null) {
            buf.append(",expr=").append(this.expr);
        }
        if (this.keyList != null) {
            buf.append(",keyList=").append(Arrays.toString(this.keyList.toArray()));
        }
        if (this.range != null) {
            buf.append(",range=").append(this.range);
        }
        buf.append(")");
        return buf.toString();
    }
}

