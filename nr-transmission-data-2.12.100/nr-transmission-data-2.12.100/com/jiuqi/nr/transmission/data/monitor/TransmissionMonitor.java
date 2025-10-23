/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundAsyncTaskException
 */
package com.jiuqi.nr.transmission.data.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundAsyncTaskException;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo;
import com.jiuqi.nr.transmission.data.monitor.TransmissionState;
import org.springframework.util.Assert;

public class TransmissionMonitor
implements AsyncTaskMonitor {
    public String taskId;
    public CacheObjectResourceRemote cacheObjectResourceRemote;
    private boolean finish = false;
    private TransmissionMonitor father = null;
    private Double monitorSize = 1.0;

    public TransmissionMonitor(String taskId, CacheObjectResourceRemote cacheObjectResourceRemote) {
        this.cacheObjectResourceRemote = cacheObjectResourceRemote;
        this.taskId = taskId;
        TransmissionMonitorInfo monitorInfo = new TransmissionMonitorInfo();
        monitorInfo.setExecuteKey(taskId);
        monitorInfo.setProcess(0.0);
        monitorInfo.setId(taskId);
        monitorInfo.setState(TransmissionState.PROCESSING);
        cacheObjectResourceRemote.create((Object)taskId, (Object)monitorInfo);
    }

    public TransmissionMonitor(String taskId, CacheObjectResourceRemote cacheObjectResourceRemote, AsyncTaskMonitor father, Double size) {
        this(taskId, cacheObjectResourceRemote);
        Assert.notNull((Object)size, "\u7236\u4efb\u52a1\u7ed9\u5b50\u4efb\u52a1\u5206\u914d\u7684\u8fdb\u5ea6\u6bd4\u7387\u4e0d\u80fd\u4e3a\u7a7a");
        if (father instanceof TransmissionMonitor) {
            Double fatherProcess = ((TransmissionMonitor)father).getProgressInfo().getProcess();
            if (fatherProcess + size >= 1.0) {
                size = 1.0 - fatherProcess;
            }
            this.father = (TransmissionMonitor)father;
            this.setMonitorSize(size);
        }
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskPoolTask() {
        return "";
    }

    public void progressAndMessage(double progress, String message) {
        String detail;
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        Double oldProcess = monitorInfo.getProcess();
        if (progress >= monitorInfo.getProcess()) {
            monitorInfo.setProcess(progress);
        }
        if ((detail = monitorInfo.getDetail()) != null && !"".equals(detail)) {
            message = detail.concat("\r\n").concat(message);
        }
        monitorInfo.setDetail(message);
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)monitorInfo);
        if (progress > oldProcess) {
            this.updateProcess(progress - oldProcess);
        }
    }

    private void updateProcess(double incProgress) {
        if (null != this.father) {
            TransmissionMonitorInfo fatherProgressInfo = this.father.getProgressInfo();
            if (incProgress >= 1.0) {
                double totalSize = fatherProgressInfo.getProcess() + this.monitorSize;
                this.father.onProgress(totalSize);
            } else {
                double reallyChildrenProcess = this.monitorSize * incProgress;
                Double fatherProcess = fatherProgressInfo.getProcess();
                double reallyProcess = fatherProcess + reallyChildrenProcess;
                if (reallyProcess > fatherProcess) {
                    this.father.onProgress(reallyProcess);
                }
            }
        }
    }

    public boolean isCancel() {
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        return TransmissionState.CANCELED.equals((Object)monitorInfo.getState());
    }

    public void finish(String result, Object detail) {
        int syncErrorNum;
        int n = syncErrorNum = detail instanceof DataImportResult ? ((DataImportResult)detail).getSyncErrorNum() : 0;
        if (syncErrorNum > 0) {
            this.pdateResultAndDetail(TransmissionState.SOMESUCCESS, this.taskId, result, detail);
        } else {
            this.pdateResultAndDetail(TransmissionState.FINISHED, this.taskId, result, detail);
        }
        this.finish = true;
    }

    private void pdateResultAndDetail(TransmissionState finished, String taskId, String result, Object detail) {
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        monitorInfo.setState(finished);
        Double oldProcess = monitorInfo.getProcess();
        monitorInfo.setProcess(1.0);
        monitorInfo.setDetail(result);
        if (detail instanceof DataImportResult) {
            monitorInfo.setResult((DataImportResult)detail);
        }
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)monitorInfo);
        if (null != this.father && 1.0 - oldProcess > 0.0) {
            this.updateProcess(1.0 - oldProcess);
        }
    }

    public void canceling(String result, Object detail) {
        this.pdateResultAndDetail(TransmissionState.CANCELING, this.taskId, result, detail);
    }

    public void canceled(String result, Object detail) {
        this.pdateResultAndDetail(TransmissionState.CANCELED, this.taskId, result, detail);
        this.finish = true;
    }

    public TransmissionMonitorInfo getProgressInfo() {
        Object find = this.cacheObjectResourceRemote.find((Object)this.taskId);
        if (null != find) {
            return (TransmissionMonitorInfo)find;
        }
        throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK, null);
    }

    public void onProgress(double progress) {
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        Double oldProcess = monitorInfo.getProcess();
        monitorInfo.setProcess(progress);
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)monitorInfo);
        if (progress > oldProcess) {
            this.updateProcess(progress - oldProcess);
        }
    }

    public void setMonitorSize(Double monitorSize) {
        this.monitorSize = monitorSize;
    }

    public void error(String cause, Throwable t) {
        if (t != null) {
            this.pdateResultAndDetail(TransmissionState.ERROR, this.taskId, cause + "\uff1a" + t.getMessage(), null);
        } else {
            this.pdateResultAndDetail(TransmissionState.ERROR, this.taskId, cause, null);
        }
    }

    public boolean isFinish() {
        return this.finish;
    }

    public double getLastProgress() {
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        return monitorInfo.getProcess();
    }
}

