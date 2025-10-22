/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 */
package com.jiuqi.nr.dataentry.print;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchPrintInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;

public interface INrEnhancedPrintService {
    public String printAsyncWork(String var1, String var2, String var3, String var4);

    public String printAsyncRequest(String var1, String var2, String var3);

    public Object changeAttribute(String var1, String var2, String var3);

    public Object result(String var1);

    public String printSetIsSetup(String var1);

    public String printResourceVersion(String var1, int var2);

    public void batchPrint(String var1, BatchPrintInfo var2);

    public ReturnInfo batchPrintTask();

    public void batchPrintRealtime(AsyncTaskMonitor var1, BatchPrintInfo var2);
}

