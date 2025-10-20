/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.np.dataengine.update.UpdateDataSet
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import java.util.List;

public class GcBdeFetchMonitor
extends AbstractMonitor
implements AsyncTaskMonitor,
IMonitor {
    public String taskId;
    private AsyncTaskInfo progressInfo;
    private boolean finish = false;

    public GcBdeFetchMonitor(String taskId) {
        this.taskId = taskId;
        AsyncTaskInfo progress = new AsyncTaskInfo();
        progress.setProcess(Double.valueOf(0.0));
        progress.setId(taskId);
        progress.setState(TaskState.PROCESSING);
        progress.setResult("");
        progress.setDetail((Object)"");
        this.progressInfo = progress;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskPoolTask() {
        return "";
    }

    public void progressAndMessage(double progress, String message) {
        if (progress >= this.progressInfo.getProcess()) {
            this.progressInfo.setProcess(Double.valueOf(progress));
        }
        this.progressInfo.setDetail((Object)message);
    }

    public AsyncTaskInfo getProgressInfo() {
        return this.progressInfo;
    }

    public boolean isCancel() {
        return TaskState.CANCELED.equals((Object)this.progressInfo.getState());
    }

    public void finish(String result, Object detail) {
        this.pdateResultAndDetail(TaskState.FINISHED, this.taskId, result, detail);
        this.finish = true;
    }

    private void pdateResultAndDetail(TaskState finished, String taskId, String result, Object detail) {
        this.progressInfo.setState(finished);
        this.progressInfo.setProcess(Double.valueOf(1.0));
        this.progressInfo.setDetail(detail);
        this.progressInfo.setResult(result);
    }

    public void canceling(String result, Object detail) {
        this.pdateResultAndDetail(TaskState.CANCELING, this.taskId, result, detail);
    }

    public void canceled(String result, Object detail) {
        this.pdateResultAndDetail(TaskState.CANCELED, this.taskId, result, detail);
        this.finish = true;
    }

    public void error(String cause, Object detail) {
        this.pdateResultAndDetail(TaskState.ERROR, this.taskId, cause, detail);
        this.finish = true;
    }

    public void start() {
        this.progressInfo.setState(TaskState.PROCESSING);
        if (null == this.progressInfo.getProcess() || this.progressInfo.getProcess() < 0.0) {
            this.progressInfo.setProcess(Double.valueOf(0.0));
        }
    }

    public void error(FormulaCheckEventImpl event) {
    }

    public void message(String msg, Object sender) {
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
    }

    public void onProgress(double progress) {
        this.progressInfo.setProcess(Double.valueOf(progress));
    }

    public void finish() {
        this.progressInfo.setProcess(Double.valueOf(1.0));
    }

    public void exception(Exception e) {
    }

    public void onDataChange(UpdateDataSet updateDatas) {
    }

    public void beforeDelete(List<DimensionValueSet> delRowKeys) {
    }

    public void error(String cause, Throwable t) {
        this.pdateResultAndDetail(TaskState.ERROR, this.taskId, cause, null);
    }

    public void error(String result, Throwable t, String detail) {
        this.progressInfo.setState(TaskState.ERROR);
        this.progressInfo.setProcess(Double.valueOf(1.0));
        this.progressInfo.setDetail((Object)detail);
        this.progressInfo.setResult(result);
    }

    public boolean beforeDeleteAll(DimensionValueSet deleteKeys) {
        return false;
    }

    public boolean isFinish() {
        return this.finish;
    }
}

