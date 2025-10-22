/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.definition.facade.FormDefine;

public class IEstimationFormImpl
implements IEstimationForm {
    private FormDefine formDefine;
    private EstimationFormType formType;

    @Override
    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    @Override
    public EstimationFormType getFormType() {
        return this.formType;
    }

    public void setFormType(EstimationFormType formType) {
        this.formType = formType;
    }
}

