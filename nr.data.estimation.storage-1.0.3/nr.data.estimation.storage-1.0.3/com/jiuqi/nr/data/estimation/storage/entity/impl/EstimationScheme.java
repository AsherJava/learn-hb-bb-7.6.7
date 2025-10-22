/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase;

public class EstimationScheme
extends EstimationSchemeBase {
    private DimensionValueSet dimValueSet;

    public EstimationScheme() {
    }

    public EstimationScheme(EstimationSchemeBase schemeBase, DimensionValueSet dimValueSet) {
        this.setKey(schemeBase.getKey());
        this.setCode(schemeBase.getCode());
        this.setTitle(schemeBase.getTitle());
        this.setTaskId(schemeBase.getTaskId());
        this.setFormSchemeId(schemeBase.getFormSchemeId());
        this.setUpdateTime(schemeBase.getUpdateTime());
        this.setCreator(schemeBase.getCreator());
        this.setOrder(schemeBase.getOrder());
        this.setFormDefines(schemeBase.getFormDefines());
        this.setAccessFormulaSchemes(schemeBase.getAccessFormulaSchemes());
        this.setCalcFormulaSchemes(schemeBase.getCalcFormulaSchemes());
        this.dimValueSet = dimValueSet;
    }

    public DimensionValueSet getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(DimensionValueSet dimValueSet) {
        this.dimValueSet = dimValueSet;
    }
}

