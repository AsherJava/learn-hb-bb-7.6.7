/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.ExceptionResult
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.uniformity.service;

import com.jiuqi.nr.common.exception.ExceptionResult;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;

public interface IDataStateCheckService {
    public String createDataCheckTag(JtableContext var1);

    public ExceptionResult checkDataState(JtableContext var1);

    public void updateDimensionCache(JtableContext var1);

    public void updateDimensionFormKeyCache(JtableContext var1);

    public void batchUpdateDimensionFormKeyCache(JtableContext var1, List<String> var2);

    public void batchUpdateDimensionCache(JtableContext var1, List<Map<String, DimensionValue>> var2);
}

