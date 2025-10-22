/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.movedata;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.movedata.ActivitiAndNrTable;

public interface IActivitiAndNrTableMoveDataService {
    public void importTable(BusinessKey var1, ActivitiAndNrTable var2);

    public ActivitiAndNrTable exportTable(BusinessKey var1, DimensionValueSet var2);
}

