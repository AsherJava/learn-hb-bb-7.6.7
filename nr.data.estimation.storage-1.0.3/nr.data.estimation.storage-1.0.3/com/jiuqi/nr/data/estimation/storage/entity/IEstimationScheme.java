/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.estimation.storage.entity;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;

public interface IEstimationScheme
extends IEstimationSchemeTemplate {
    public String getUnit();

    public String getPeriod();

    public DimensionValueSet getDimValueSet();
}

