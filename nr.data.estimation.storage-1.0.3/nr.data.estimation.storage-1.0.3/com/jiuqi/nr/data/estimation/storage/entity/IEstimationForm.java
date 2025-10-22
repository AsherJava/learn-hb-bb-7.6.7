/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.estimation.storage.entity;

import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.definition.facade.FormDefine;

public interface IEstimationForm {
    public FormDefine getFormDefine();

    public EstimationFormType getFormType();
}

