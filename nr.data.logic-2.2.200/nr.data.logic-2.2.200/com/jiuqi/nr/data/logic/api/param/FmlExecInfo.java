/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.data.logic.api.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.List;

public class FmlExecInfo {
    private DimensionValueSet dimensionValueSet;
    private List<IParsedExpression> parsedExpressions;

    public FmlExecInfo() {
    }

    public FmlExecInfo(DimensionValueSet dimensionValueSet, List<IParsedExpression> parsedExpressions) {
        this.dimensionValueSet = dimensionValueSet;
        this.parsedExpressions = parsedExpressions;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public List<IParsedExpression> getParsedExpressions() {
        return this.parsedExpressions;
    }

    public void setParsedExpressions(List<IParsedExpression> parsedExpressions) {
        this.parsedExpressions = parsedExpressions;
    }
}

