/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public interface IFmlExecInfoProvider {
    public String getFormulaSchemeKey();

    public DimensionCollection getDimensionCollection();

    public List<FmlExecInfo> getFmlExecInfo();

    default public boolean fmlJIT() {
        return false;
    }

    default public ExecutorContext getFmlExecutorContext(ExecutorContext executorContext) {
        return executorContext;
    }
}

