/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.bean.RealTimeJobBean
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.bi.core.jobs.realtime.bean.RealTimeJobBean;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;
import com.jiuqi.np.asynctask.exception.TaskExsitsException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface AsyncTaskManager {
    @Deprecated
    public String publishTask(Object var1, String var2) throws NpAsyncTaskExecption;

    public String publishTask(NpRealTimeTaskInfo var1) throws NpAsyncTaskExecption;

    @Deprecated
    public String publishTask(NpRealTimeTaskInfo var1, String var2) throws NpAsyncTaskExecption;

    @Deprecated
    public String publishAndExecuteTask(Object var1, String var2) throws NpAsyncTaskExecption;

    @Deprecated
    public String publishToSplitQueue(Object var1, String var2) throws NpAsyncTaskExecption;

    public void cancelTask(String var1);

    @Deprecated
    public String publishTaskWithDepend(Object var1, String var2, String var3) throws NpAsyncTaskExecption;

    @Deprecated
    public String publishUniqueTask(Object var1, String var2, String var3) throws NpAsyncTaskExecption, TaskExsitsException;

    public String publishUniqueTask(NpRealTimeTaskInfo var1, String var2, String var3);

    public TaskState queryTaskState(String var1);

    public Integer queryLocation(String var1);

    public Integer queryLocation(AsyncTask var1);

    public Double queryProcess(String var1);

    public String queryResult(String var1);

    public Object queryDetail(String var1);

    public String queryDetailString(String var1);

    public Serializable querySerializableDetail(String var1) throws NpAsyncTaskExecption;

    public String queryArgs(String var1);

    public AsyncTask queryTask(String var1);

    public AsyncTask querySimpleTask(String var1);

    public Map<String, AsyncTask> batchQuerySimpleTask(List<String> var1);

    public List<AsyncTask> queryTaskToDo();

    public List<AsyncTask> queryTaskToDoWithoutClob();

    public int completeTask(String var1);

    public Map<String, Object> queryDetails(List<String> var1);

    public Map<String, String> queryDetailStrings(List<String> var1);

    public String queryTaskKey(String var1);

    public String queryFormSchemeKey(String var1);

    public List<RealTimeJobBean> getCancelableTasks();
}

