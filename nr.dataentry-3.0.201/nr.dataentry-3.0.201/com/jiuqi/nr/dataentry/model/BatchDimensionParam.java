/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.model;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;

public class BatchDimensionParam {
    private List<Map<String, DimensionValue>> list;
    private List<String> entitys;
    private String date;

    public List<Map<String, DimensionValue>> getList() {
        return this.list;
    }

    public void setList(List<Map<String, DimensionValue>> list) {
        this.list = list;
    }

    public List<String> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<String> entitys) {
        this.entitys = entitys;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

