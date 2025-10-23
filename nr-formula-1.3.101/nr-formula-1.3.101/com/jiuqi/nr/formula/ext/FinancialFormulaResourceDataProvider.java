/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 */
package com.jiuqi.nr.formula.ext;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.formula.ext.FormulaResourceDataProvider;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;

public class FinancialFormulaResourceDataProvider
extends FormulaResourceDataProvider {
    public FinancialFormulaResourceDataProvider(IFormulaSchemeService formulaSchemeService, IDesignTimeViewController designTimeViewController) {
        super(formulaSchemeService, designTimeViewController);
    }

    @Override
    protected FormulaSchemeType getFormulaSchemeType() {
        return FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL;
    }

    @Override
    public String getTypeCode() {
        return "FINANCIAL_FORMULA_RESOURCE";
    }

    @Override
    protected String getIcon() {
        return "#icon-J_GJ_A_NR_caiwugongshifanganleixing";
    }
}

