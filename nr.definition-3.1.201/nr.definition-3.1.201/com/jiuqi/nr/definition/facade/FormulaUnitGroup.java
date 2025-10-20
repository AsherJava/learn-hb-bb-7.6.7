/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.List;

public class FormulaUnitGroup {
    private String unit;
    private List<IParsedExpression> formulaList;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<IParsedExpression> getFormulaList() {
        return this.formulaList;
    }

    public void setFormulaList(List<IParsedExpression> formulaList) {
        this.formulaList = formulaList;
    }
}

