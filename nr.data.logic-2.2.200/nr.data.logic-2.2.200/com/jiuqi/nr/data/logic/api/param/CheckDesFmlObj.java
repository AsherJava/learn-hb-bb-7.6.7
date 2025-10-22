/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.logic.api.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import java.util.List;
import java.util.Map;

public class CheckDesFmlObj
extends CheckDesObj {
    private final CheckDesObj checkDesObj;
    private final IParsedExpression parsedExpression;

    public CheckDesFmlObj(CheckDesObj checkDesObj, IParsedExpression parsedExpression) {
        this.checkDesObj = checkDesObj;
        this.parsedExpression = parsedExpression;
    }

    @Override
    public CheckDescription getCheckDescription() {
        return this.checkDesObj.getCheckDescription();
    }

    @Override
    public Map<String, DimensionValue> getDimensionSet() {
        return this.checkDesObj.getDimensionSet();
    }

    @Override
    public DimensionValueSet getDimensionValueSet() {
        return this.checkDesObj.getDimensionValueSet();
    }

    @Override
    public DimensionValueSet getDimensionValueSet(List<String> dimNames) {
        return this.checkDesObj.getDimensionValueSet(dimNames);
    }

    @Override
    public String getFloatId() {
        return this.checkDesObj.getFloatId();
    }

    @Override
    public String getFormKey() {
        return this.checkDesObj.getFormKey();
    }

    @Override
    public String getFormulaCode() {
        return this.checkDesObj.getFormulaCode();
    }

    @Override
    public String getFormulaExpressionKey() {
        return this.checkDesObj.getFormulaExpressionKey();
    }

    @Override
    public String getFormulaSchemeKey() {
        return this.checkDesObj.getFormulaSchemeKey();
    }

    @Override
    public int getGlobCol() {
        return this.checkDesObj.getGlobCol();
    }

    @Override
    public int getGlobRow() {
        return this.checkDesObj.getGlobRow();
    }

    @Override
    public String getRecordId() {
        return this.checkDesObj.getRecordId();
    }

    public IParsedExpression getParsedExpression() {
        return this.parsedExpression;
    }
}

