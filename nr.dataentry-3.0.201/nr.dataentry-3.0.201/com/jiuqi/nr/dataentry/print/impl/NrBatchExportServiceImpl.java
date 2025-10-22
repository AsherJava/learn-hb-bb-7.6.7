/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.xg.print.PrinterDevice
 */
package com.jiuqi.nr.dataentry.print.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchPrintInfo;
import com.jiuqi.nr.dataentry.paramInfo.PrintSumCover;
import com.jiuqi.nr.dataentry.print.impl.AbstractNrEnhancedPrintService;
import com.jiuqi.nr.dataentry.service.IBatchExportService;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.xg.print.PrinterDevice;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class NrBatchExportServiceImpl
extends AbstractNrEnhancedPrintService
implements IBatchExportService {
    @Override
    public void export(BatchExportInfo batchExportInfo, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        this.defaultBatchExportService.export(batchExportInfo, asyncTaskMonitor);
    }

    @Override
    public ReturnInfo batchPrintTask() {
        return this.getINrEnhancedPrintService().batchPrintTask();
    }

    @Override
    public void batchPrint(BatchPrintInfo info, PrinterDevice printerDevice, AsyncTaskMonitor asyncTaskMonitor) {
        this.defaultBatchExportService.batchPrint(info, printerDevice, asyncTaskMonitor);
    }

    @Override
    public void batchPrint(String taskId, BatchPrintInfo info) {
        this.getINrEnhancedPrintService().batchPrint(taskId, info);
    }

    @Override
    public void batchPrintRealtime(AsyncTaskMonitor asyncTaskMonitor, BatchPrintInfo info) {
        this.getINrEnhancedPrintService().batchPrintRealtime(asyncTaskMonitor, info);
    }

    @Override
    public byte[] printSumCover(PrintSumCover param) {
        return this.defaultBatchExportService.printSumCover(param);
    }
}

