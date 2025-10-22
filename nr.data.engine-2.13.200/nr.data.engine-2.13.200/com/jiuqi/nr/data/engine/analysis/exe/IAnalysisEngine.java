/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.engine.analysis.define.AnalysisModel;

public interface IAnalysisEngine {
    public void prepare(ExecutorContext var1, AnalysisModel var2) throws Exception;

    public void execute(DimensionValueSet var1, DimensionValueSet var2) throws Exception;
}

