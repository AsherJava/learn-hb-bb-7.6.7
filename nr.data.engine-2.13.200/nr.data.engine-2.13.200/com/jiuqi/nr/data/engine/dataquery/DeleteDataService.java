/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.dataquery;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public interface DeleteDataService {
    public void deleteDataByTaskKey(String var1) throws Exception;

    public void deleteDataByTaskKey(String var1, boolean var2) throws Exception;

    public void deleteDataByFormKey(String var1) throws Exception;

    public void deleteDataByTaskDim(String var1, DimensionValueSet var2) throws Exception;

    public void deleteDataByFormDim(String var1, DimensionValueSet var2) throws Exception;

    public void deleteEntityByTaskKey(String var1) throws Exception;
}

