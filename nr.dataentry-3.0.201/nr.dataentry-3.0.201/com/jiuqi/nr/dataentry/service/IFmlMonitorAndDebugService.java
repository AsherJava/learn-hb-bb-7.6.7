/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.jtable.params.base.FormulaData
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.paramInfo.FmlDebugNode;
import com.jiuqi.nr.dataentry.paramInfo.FmlMonitorAndDebugParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import java.util.List;

public interface IFmlMonitorAndDebugService {
    public void runFmlMonitor(FmlMonitorAndDebugParam var1, AsyncTaskMonitor var2);

    public String previewFmlMonitorResult(String var1);

    public List<ExportData> exportFmlMonitorResult(String var1);

    public List<FormulaData> queryFmlList(FmlMonitorAndDebugParam var1);

    public FmlDebugNode queryFmlValue(FmlMonitorAndDebugParam var1);

    public String getDimsTitle(String var1, DimensionCombination var2);
}

