/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 */
package com.jiuqi.nr.dataentry.copydes;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import java.util.List;
import java.util.Map;

public class CheckDesFmlObj
extends CheckDesObj {
    private CheckDesObj checkDesObj;
    private IParsedExpression parsedExpression;

    public CheckDesFmlObj(CheckDesObj checkDesObj, IParsedExpression parsedExpression) {
        this.checkDesObj = checkDesObj;
        this.parsedExpression = parsedExpression;
    }

    public CheckDescription getCheckDescription() {
        return this.checkDesObj.getCheckDescription();
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.checkDesObj.getDimensionSet();
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.checkDesObj.getDimensionValueSet();
    }

    public DimensionValueSet getDimensionValueSet(List<String> dimNames) {
        return this.checkDesObj.getDimensionValueSet(dimNames);
    }

    public String getFloatId() {
        return this.checkDesObj.getFloatId();
    }

    public String getFormKey() {
        return this.checkDesObj.getFormKey();
    }

    public String getFormulaCode() {
        return this.checkDesObj.getFormulaCode();
    }

    public String getFormulaExpressionKey() {
        return this.checkDesObj.getFormulaExpressionKey();
    }

    public String getFormulaSchemeKey() {
        return this.checkDesObj.getFormulaSchemeKey();
    }

    public int getGlobCol() {
        return this.checkDesObj.getGlobCol();
    }

    public int getGlobRow() {
        return this.checkDesObj.getGlobRow();
    }

    public String getRecordId() {
        return this.checkDesObj.getRecordId();
    }

    public IParsedExpression getParsedExpression() {
        return this.parsedExpression;
    }
}

