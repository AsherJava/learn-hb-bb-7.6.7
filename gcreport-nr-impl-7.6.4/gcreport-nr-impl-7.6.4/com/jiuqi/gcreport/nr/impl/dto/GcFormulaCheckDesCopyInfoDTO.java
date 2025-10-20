/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.gcreport.nr.impl.dto;

import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesCopyInfoParam;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.io.Serializable;

public class GcFormulaCheckDesCopyInfoDTO
implements Serializable {
    private GcFormulaCheckDesCopyInfoParam formulaCheckDesCopyInfoParam;
    private AsyncTaskMonitor asyncTaskMonitor;

    public GcFormulaCheckDesCopyInfoParam getFormulaCheckDesCopyInfoParam() {
        return this.formulaCheckDesCopyInfoParam;
    }

    public void setFormulaCheckDesCopyInfoParam(GcFormulaCheckDesCopyInfoParam formulaCheckDesCopyInfoParam) {
        this.formulaCheckDesCopyInfoParam = formulaCheckDesCopyInfoParam;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }
}

