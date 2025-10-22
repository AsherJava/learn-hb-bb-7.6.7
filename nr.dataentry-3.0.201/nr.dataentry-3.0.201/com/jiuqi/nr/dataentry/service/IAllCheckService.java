/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.paramInfo.AllCheckInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;

@Deprecated
public interface IAllCheckService {
    @Deprecated
    public void allCheckForm(AllCheckInfo var1, AsyncTaskMonitor var2);

    @Deprecated
    public FormulaCheckReturnInfo allCheckResult(AllCheckInfo var1);
}

