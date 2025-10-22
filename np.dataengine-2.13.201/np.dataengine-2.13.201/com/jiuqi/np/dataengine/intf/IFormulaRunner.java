/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;

public interface IFormulaRunner {
    public void prepareCalc(ExecutorContext var1, DimensionValueSet var2, IMonitor var3) throws Exception;

    public void prepareCheck(ExecutorContext var1, DimensionValueSet var2, IMonitor var3) throws Exception;

    public void setMasterKeyValues(DimensionValueSet var1);

    public void run(IMonitor var1) throws Exception;

    public void setMultiDimModule(boolean var1);
}

