/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable
 *  com.jiuqi.nvwa.dispatch.core.task.ITaskListener
 *  com.jiuqi.xg.print.PrinterDevice
 *  com.jiuqi.xg.print.util.AsyncWorkContainnerUtil
 */
package com.jiuqi.nr.dataentry.print.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchPrintInfo;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.print.INrEnhancedPrintService;
import com.jiuqi.nr.dataentry.print.impl.NrEnhancedPrintHelper;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable;
import com.jiuqi.nvwa.dispatch.core.task.ITaskListener;
import com.jiuqi.xg.print.PrinterDevice;
import com.jiuqi.xg.print.util.AsyncWorkContainnerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dispatchable(name="nrEnhancedPrintService", title="\u65b0\u62a5\u8868\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668", expireTime=300000L)
public class NrEnhancedPrintServiceImpl
implements INrEnhancedPrintService,
ITaskListener {
    private Logger logger = LoggerFactory.getLogger(NrEnhancedPrintServiceImpl.class);

    public void onTaskStart(ITaskContext context) throws TaskException {
        this.logger.info("\u65b0\u62a5\u8868\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668: \u521d\u59cb\u5316\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668.");
    }

    public void onTaskEnd(ITaskContext context) throws TaskException {
        this.logger.info("\u65b0\u62a5\u8868\u6269\u5c55\u6253\u5370\u670d\u52a1\u5668: \u6269\u5c55\u6253\u5370\u670d\u52a1\u5668\u7ec8\u6b62.");
    }

    @Override
    public String printAsyncWork(String type, String printerID, String workName, String result) {
        return NrEnhancedPrintHelper.getiPrintService().printAsyncWork(type, printerID, workName, result);
    }

    @Override
    public String printAsyncRequest(String printerID, String data, String docId) {
        return NrEnhancedPrintHelper.getiPrintService().printAsyncRequest(printerID, data, docId);
    }

    @Override
    public String changeAttribute(String printerID, String changeType, String changeValue) {
        return this.toString(NrEnhancedPrintHelper.getiPrintService().changeAttribute(printerID, changeType, changeValue));
    }

    @Override
    public String result(String printerID) {
        return this.toString(NrEnhancedPrintHelper.getiPrintService().result(printerID));
    }

    private String toString(Object obj) {
        if (null == obj) {
            return null;
        }
        return obj.toString();
    }

    @Override
    public String printSetIsSetup(String printerID) {
        return NrEnhancedPrintHelper.getiPrintService().printSetIsSetup(printerID);
    }

    @Override
    public String printResourceVersion(String printerID, int localVersion) {
        return NrEnhancedPrintHelper.getiPrintService().printResourceVersion(printerID, localVersion);
    }

    @Override
    public ReturnInfo batchPrintTask() {
        return NrEnhancedPrintHelper.getiBatchExportService().batchPrintTask();
    }

    @Override
    public void batchPrintRealtime(AsyncTaskMonitor asyncTaskMonitor, BatchPrintInfo info) {
        PrinterDevice printerDevice = AsyncWorkContainnerUtil.getPrinterDevice((String)info.getPrinterID());
        NrEnhancedPrintHelper.getiBatchExportService().batchPrint(info, printerDevice, asyncTaskMonitor);
    }

    @Override
    public void batchPrint(String taskId, BatchPrintInfo info) {
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, NrEnhancedPrintHelper.getCacheObjectResourceRemote());
        PrinterDevice printerDevice = AsyncWorkContainnerUtil.getPrinterDevice((String)info.getPrinterID());
        NrEnhancedPrintHelper.getiBatchExportService().batchPrint(info, printerDevice, asyncTaskMonitor);
    }
}

