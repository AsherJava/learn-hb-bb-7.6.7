/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import com.jiuqi.nr.data.logic.facade.extend.param.AutoCalStrategy;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public abstract class BaseCalFormFmlParam
extends BaseFmlFactoryParam {
    private static final long serialVersionUID = -6594813480024715311L;
    protected String formulaSchemeKey;
    protected DimensionCombination dimensionCombination;
    protected String formKey;

    public AutoCalStrategy getStrategy() {
        throw new UnsupportedOperationException();
    }

    public AutoCalStrategy getDowngradeStrategy() {
        return null;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

