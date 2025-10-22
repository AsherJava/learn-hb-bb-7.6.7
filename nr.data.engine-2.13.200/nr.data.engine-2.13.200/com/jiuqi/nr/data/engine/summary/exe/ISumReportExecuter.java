/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.data.engine.summary.exe;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.engine.summary.exe.SumCell;
import java.util.List;

public interface ISumReportExecuter {
    public void doExecute(ExecutorContext var1) throws Exception;

    public List<SumCell> getCells();

    public void setFmlFilter(String var1);

    public void setSrcDimension(DimensionValueSet var1);

    public void setDestDimension(DimensionValueSet var1);

    public void setParallelSize(int var1);
}

