/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.storage.entity.impl.IEstimationSchemeTemplateImpl;

public class IEstimationSchemeImpl
extends IEstimationSchemeTemplateImpl
implements IEstimationScheme {
    private DimensionValueSet dimValueSet;

    public IEstimationSchemeImpl(IEstimationSchemeTemplateImpl template, DimensionValueSet dimValueSet) {
        this.setKey(template.getKey());
        this.setCode(template.getCode());
        this.setTitle(template.getTitle());
        this.setUpdateTime(template.getUpdateTime());
        this.setCreator(template.getCreator());
        this.setOrder(template.getOrder());
        this.setTaskDefine(template.getTaskDefine());
        this.setFormSchemeDefine(template.getFormSchemeDefine());
        this.setEstimationForms(template.getEstimationForms());
        this.setAccessFormulaSchemes(template.getAccessFormulaSchemes());
        this.setCalcFormulaSchemes(template.getCalcFormulaSchemes());
        this.dimValueSet = dimValueSet;
    }

    public IEstimationSchemeImpl() {
    }

    @Override
    public String getUnit() {
        return null;
    }

    @Override
    public String getPeriod() {
        return null;
    }

    @Override
    public DimensionValueSet getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(DimensionValueSet dimValueSet) {
        this.dimValueSet = dimValueSet;
    }
}

