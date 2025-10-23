/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.transmission.data.intf.DataImportResult
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo
 *  com.jiuqi.nr.transmission.data.monitor.TransmissionState
 */
package com.jiuqi.nr.nrdx.data.nrd;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitor;
import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo;
import com.jiuqi.nr.transmission.data.monitor.TransmissionState;

public class NrdMonitor
extends TransmissionMonitor {
    private final AsyncTaskMonitor monitor;
    private boolean finish = false;

    public NrdMonitor(String taskId, CacheObjectResourceRemote cacheObjectResourceRemote, AsyncTaskMonitor father, Double size) {
        super(taskId, cacheObjectResourceRemote);
        this.monitor = father;
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
        if (progress >= monitorInfo.getProcess()) {
            monitorInfo.setProcess(Double.valueOf(progress));
        }
        if ((detail = monitorInfo.getDetail()) != null && !detail.isEmpty()) {
            message = detail.concat("\r\n").concat(message);
        }
        monitorInfo.setDetail(message);
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)monitorInfo);
        this.monitor.progressAndMessage(progress, "NRD\u5bfc\u5165\u8fdb\u884c\u4e2d");
    }

    public boolean isCancel() {
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        return TransmissionState.CANCELED.equals((Object)monitorInfo.getState()) || this.monitor.isCancel();
    }

    public void finish(String result, Object detail) {
        int syncErrorNum;
        int n = syncErrorNum = detail instanceof DataImportResult ? ((DataImportResult)detail).getSyncErrorNum() : 0;
        if (syncErrorNum > 0) {
            this.pdateResultAndDetail(TransmissionState.SOMESUCCESS, result, detail);
        } else {
            this.pdateResultAndDetail(TransmissionState.FINISHED, result, detail);
        }
        if (result != null && result.length() > 200) {
            result = result.substring(0, 200);
        }
        this.monitor.finish(result, detail);
        this.finish = true;
    }

    private void pdateResultAndDetail(TransmissionState finished, String result, Object detail) {
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        monitorInfo.setState(finished);
        monitorInfo.setProcess(Double.valueOf(1.0));
        monitorInfo.setDetail(result);
        if (detail instanceof DataImportResult) {
            monitorInfo.setResult((DataImportResult)detail);
        }
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)monitorInfo);
    }

    public void canceling(String result, Object detail) {
        this.pdateResultAndDetail(TransmissionState.CANCELING, result, detail);
        if (result != null && result.length() > 200) {
            result = result.substring(0, 200);
        }
        this.monitor.canceling(result, detail);
    }

    public void canceled(String result, Object detail) {
        this.pdateResultAndDetail(TransmissionState.CANCELED, result, detail);
        if (result != null && result.length() > 200) {
            result = result.substring(0, 200);
        }
        this.monitor.canceled(result, detail);
        this.finish = true;
    }

    public void onProgress(double progress) {
        TransmissionMonitorInfo monitorInfo = this.getProgressInfo();
        monitorInfo.setProcess(Double.valueOf(progress));
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)monitorInfo);
        this.monitor.progressAndMessage(progress, "NRD\u5bfc\u5165\u8fdb\u884c\u4e2d");
        if (progress >= 1.0) {
            String result = "";
            if (monitorInfo.getDetail() != null) {
                result = monitorInfo.getDetail().length() > 200 ? monitorInfo.getDetail().substring(0, 200) : monitorInfo.getDetail();
            }
            this.monitor.finish(result, (Object)monitorInfo.getResult());
            this.finish = true;
        }
    }

    public void error(String cause, Throwable t) {
        if (t != null) {
            cause = cause + "\uff1a" + t.getMessage();
            this.pdateResultAndDetail(TransmissionState.ERROR, cause, null);
        } else {
            this.pdateResultAndDetail(TransmissionState.ERROR, cause, null);
        }
        if (cause != null && cause.length() > 200) {
            cause = cause.substring(0, 200);
        }
        this.monitor.error(cause, t);
    }

    public boolean isFinish() {
        return this.finish;
    }

    public double getLastProgress() {
        return this.monitor.getLastProgress();
    }
}

