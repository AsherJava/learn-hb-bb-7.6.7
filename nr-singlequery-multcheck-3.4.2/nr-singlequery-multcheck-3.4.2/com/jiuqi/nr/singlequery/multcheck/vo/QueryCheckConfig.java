/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacheck.dataanalyze.CheckCondition
 */
package com.jiuqi.nr.singlequery.multcheck.vo;

import com.jiuqi.nr.datacheck.dataanalyze.CheckCondition;
import com.jiuqi.nr.singlequery.multcheck.vo.QueryCheckModel;
import java.util.List;

public class QueryCheckConfig {
    private CheckCondition checkRequires;
    private List<QueryCheckModel> models;
    private int unitRange;

    public int getUnitRange() {
        return this.unitRange;
    }

    public void setUnitRange(int unitRange) {
        this.unitRange = unitRange;
    }

    public CheckCondition getCheckRequires() {
        return this.checkRequires;
    }

    public void setCheckRequires(CheckCondition checkRequires) {
        this.checkRequires = checkRequires;
    }

    public List<QueryCheckModel> getModels() {
        return this.models;
    }

    public void setModels(List<QueryCheckModel> models) {
        this.models = models;
    }
}

