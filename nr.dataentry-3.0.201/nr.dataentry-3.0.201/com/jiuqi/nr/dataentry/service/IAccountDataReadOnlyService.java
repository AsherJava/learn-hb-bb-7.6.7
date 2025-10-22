/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.readwrite.bean.BatchAccountDataReadWriteResult;
import java.util.List;
import java.util.Map;

public interface IAccountDataReadOnlyService {
    public Boolean readOnly(DimensionValueSet var1, String var2);

    public Map<DimensionValueSet, Boolean> batchReadOnly(DimensionValueSet var1, String var2);

    public List<DimensionValueSet> batchReadOnlyDimensions(DimensionValueSet var1, String var2);

    public List<BatchAccountDataReadWriteResult> batchReadOnlyAccResult(DimensionValueSet var1, List<String> var2);
}

