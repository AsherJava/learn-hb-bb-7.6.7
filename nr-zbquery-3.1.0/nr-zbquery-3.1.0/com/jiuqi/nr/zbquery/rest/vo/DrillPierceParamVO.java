/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;

public class DrillPierceParamVO {
    private ZBQueryModel queryModel;
    private ConditionValues conditionValues;
    private String rowDims;
    private String[] colNames;

    public ZBQueryModel getQueryModel() {
        return this.queryModel;
    }

    public void setQueryModel(ZBQueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public ConditionValues getConditionValues() {
        return this.conditionValues;
    }

    public void setConditionValues(ConditionValues conditionValues) {
        this.conditionValues = conditionValues;
    }

    public String getRowDims() {
        return this.rowDims;
    }

    public void setRowDims(String rowDims) {
        this.rowDims = rowDims;
    }

    public String[] getColNames() {
        return this.colNames;
    }

    public void setColNames(String[] colNames) {
        this.colNames = colNames;
    }
}

