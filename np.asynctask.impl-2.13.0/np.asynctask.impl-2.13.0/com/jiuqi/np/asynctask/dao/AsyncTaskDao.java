/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.TaskState
 */
package com.jiuqi.np.asynctask.dao;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.TaskState;
import java.util.List;
import java.util.Map;

public interface AsyncTaskDao {
    public void insert(AsyncTask var1);

    public void updateProgressAndMessage(String var1, double var2, String var4);

    public void updateResultAndDetail(TaskState var1, String var2, String var3, Object var4);

    public void updateState(TaskState var1, String var2);

    public void updateEffectTime(String var1);

    public void updateEffectTime(String var1, String var2);

    public void updateOverTimeState();

    public void updateOverTimeState(String var1);

    public void deleteHistoryData(long var1);

    public void insertHistoryData(long var1);

    public AsyncTask query(String var1);

    public AsyncTask querySimpleTask(String var1);

    public Map<String, AsyncTask> batchQuerySimpleTasks(List<String> var1);

    public List<AsyncTask> queryToDo();

    public List<AsyncTask> queryTaskToDoWithoutClob();

    public List<AsyncTask> queryTaskToDoWithoutClob(String var1);

    public List<AsyncTask> queryTaskToDoWithoutClob(String var1, List<String> var2);

    public List<AsyncTask> queryTaskToDoWithoutClob(String var1, String var2);

    public List<AsyncTask> queryTaskToDoWithoutClob(String var1, String var2, List<String> var3);

    public Integer queryLocation(String var1, AsyncTask var2);

    public TaskState queryState(String var1);

    public List<AsyncTask> queryByTaskPool(String var1, TaskState var2);

    public AsyncTask queryPrevTaskPool(String var1);

    @Deprecated
    public Object queryDetail(String var1);

    public String queryDetailString(String var1);

    public String queryArgs(String var1);

    public void updateErrorInfo(String var1, String var2, Throwable var3, Object var4);

    public List<String> queryTaskIdsToClear(String var1, long var2);

    public void deleteHistoryTableData(long var1);

    public String isUniqueTaskExsits(String var1, String var2);

    public void insertTaskCompleteFlag(String var1, long var2);

    public int updateCompleteTask(String var1);

    public void deleteStaleCompleteFlag(long var1);

    public void deleteJunkCompleteFlag();

    public Map<String, Object> queryDetails(List<String> var1);

    public Map<String, String> queryDetailStrings(List<String> var1);

    public String queryTaskKey(String var1);

    public String queryFormSchemeKey(String var1);
}

