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
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundAsyncTaskException
 */
package com.jiuqi.nr.dataentry.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundAsyncTaskException;
import java.io.Serializable;
import java.util.List;

public class SimpleAsyncProgressMonitor
extends AbstractMonitor
implements AsyncTaskMonitor,
IMonitor,
Serializable {
    private static final long serialVersionUID = -3834849072737749087L;
    public String taskId;
    public CacheObjectResourceRemote cacheObjectResourceRemote;
    private boolean finish = false;
    private SimpleAsyncProgressMonitor father = null;

    public SimpleAsyncProgressMonitor(String taskId, CacheObjectResourceRemote cacheObjectResourceRemote) {
        this.cacheObjectResourceRemote = cacheObjectResourceRemote;
        this.taskId = taskId;
        AsyncTaskInfo progressInfo = new AsyncTaskInfo();
        progressInfo.setProcess(Double.valueOf(0.0));
        progressInfo.setId(taskId);
        progressInfo.setState(TaskState.PROCESSING);
        progressInfo.setUrl("/api/v1/dataentry/actions/progress/query?progressId=");
        cacheObjectResourceRemote.create((Object)taskId, (Object)progressInfo);
    }

    public SimpleAsyncProgressMonitor(String taskId, CacheObjectResourceRemote CacheObjectResourceRemote2, AsyncTaskMonitor father) {
        this(taskId, CacheObjectResourceRemote2);
        if (father instanceof SimpleAsyncProgressMonitor) {
            this.father = (SimpleAsyncProgressMonitor)father;
        }
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskPoolTask() {
        return "";
    }

    public void progressAndMessage(double progress, String message) {
        AsyncTaskInfo progressInfo = this.getProgressInfo();
        if (progress >= progressInfo.getProcess()) {
            progressInfo.setProcess(Double.valueOf(progress));
        }
        progressInfo.setDetail((Object)message);
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)progressInfo);
        if (null != this.father) {
            AsyncTaskInfo fatherProgressInfo = this.father.getProgressInfo();
            if (progress >= 1.0) {
                this.father.onProgress(1.0);
            } else {
                Double process = fatherProgressInfo.getProcess();
                Double surplus = 1.0 - process;
                if (surplus > 0.0) {
                    surplus = progress * surplus;
                    if ((surplus = Double.valueOf(progress + surplus)) > process) {
                        this.father.onProgress(surplus);
                    }
                }
            }
        }
    }

    public boolean isCancel() {
        AsyncTaskInfo progressInfo = this.getProgressInfo();
        return TaskState.CANCELED.equals((Object)progressInfo.getState());
    }

    public void finish(String result, Object detail) {
        this.pdateResultAndDetail(TaskState.FINISHED, this.taskId, result, detail);
        this.finish = true;
    }

    private void pdateResultAndDetail(TaskState finished, String taskId, String result, Object detail) {
        AsyncTaskInfo progressInfo = this.getProgressInfo();
        progressInfo.setState(finished);
        progressInfo.setProcess(Double.valueOf(1.0));
        progressInfo.setDetail(detail);
        progressInfo.setResult(result);
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)progressInfo);
        if (null != this.father) {
            this.father.onProgress(1.0);
        }
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

    public AsyncTaskInfo getProgressInfo() {
        Object find = this.cacheObjectResourceRemote.find((Object)this.taskId);
        if (null != find) {
            return (AsyncTaskInfo)find;
        }
        throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK, null);
    }

    public void start() {
        AsyncTaskInfo progressInfo = this.getProgressInfo();
        progressInfo.setState(TaskState.PROCESSING);
        if (null == progressInfo.getProcess() || progressInfo.getProcess() < 0.0) {
            progressInfo.setProcess(Double.valueOf(0.0));
        }
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)progressInfo);
    }

    public void error(FormulaCheckEventImpl event) {
    }

    public void message(String msg, Object sender) {
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
    }

    public void onProgress(double progress) {
        AsyncTaskInfo progressInfo = this.getProgressInfo();
        progressInfo.setProcess(Double.valueOf(progress));
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)progressInfo);
        if (null != this.father) {
            AsyncTaskInfo fatherProgressInfo = this.father.getProgressInfo();
            if (progress >= 1.0) {
                this.father.onProgress(1.0);
            } else {
                Double process = fatherProgressInfo.getProcess();
                Double surplus = 1.0 - process;
                if (surplus > 0.0) {
                    surplus = progress * surplus;
                    if ((surplus = Double.valueOf(progress + surplus)) > process) {
                        this.father.onProgress(surplus);
                    }
                }
            }
        }
    }

    public void finish() {
        AsyncTaskInfo progressInfo = this.getProgressInfo();
        progressInfo.setProcess(Double.valueOf(1.0));
        this.cacheObjectResourceRemote.create((Object)this.taskId, (Object)progressInfo);
        if (null != this.father) {
            this.father.onProgress(1.0);
            super.finish();
        }
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

    public boolean beforeDeleteAll(DimensionValueSet deleteKeys) {
        return false;
    }

    public boolean isFinish() {
        return this.finish;
    }
}

