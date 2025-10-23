/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.api;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.common.StateConst;
import java.util.Map;

public interface IStateEndUploadService {
    public boolean saveData(String var1, DimensionValueSet var2, int var3);

    public Map<DimensionValueSet, StateConst> getStatesInfo(String var1, DimensionValueSet var2, String var3);

    public void deleteData(String var1, DimensionValueSet var2);
}

