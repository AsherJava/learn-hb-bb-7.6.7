/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.nr.dataentry.service.IAsyncExpService
 */
package com.jiuqi.nr.multcheck2.asynctask;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.nr.dataentry.service.IAsyncExpService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MCDataEntryBatchAsyncExpServiceImpl
implements IAsyncExpService {
    @Autowired
    AsyncTaskDao asyncTaskDao;

    public List<AsyncTask> getExpAsyncTasks(String taskKey) {
        ArrayList<String> taskPoolTypes = new ArrayList<String>();
        taskPoolTypes.add("ASYNCTASK_DATAENTRY_MULTCHECK_BATCH");
        List queryTaskToDo = this.asyncTaskDao.queryTaskToDoWithoutClob(taskKey, taskPoolTypes);
        ArrayList<AsyncTask> dataentryAsyncTask = new ArrayList<AsyncTask>();
        if (!CollectionUtils.isEmpty(queryTaskToDo)) {
            dataentryAsyncTask.add((AsyncTask)queryTaskToDo.get(0));
        }
        return dataentryAsyncTask;
    }
}

