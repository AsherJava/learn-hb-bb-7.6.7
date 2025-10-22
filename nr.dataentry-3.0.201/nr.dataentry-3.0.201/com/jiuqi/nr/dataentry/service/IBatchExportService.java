/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.xg.print.PrinterDevice
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchPrintInfo;
import com.jiuqi.nr.dataentry.paramInfo.PrintSumCover;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.xg.print.PrinterDevice;

public interface IBatchExportService {
    public void export(BatchExportInfo var1, AsyncTaskMonitor var2) throws Exception;

    public ReturnInfo batchPrintTask();

    public void batchPrint(BatchPrintInfo var1, PrinterDevice var2, AsyncTaskMonitor var3);

    default public void batchPrint(String taskId, BatchPrintInfo info) {
    }

    default public void batchPrintRealtime(AsyncTaskMonitor asyncTaskMonitor, BatchPrintInfo info) {
    }

    public byte[] printSumCover(PrintSumCover var1);
}

