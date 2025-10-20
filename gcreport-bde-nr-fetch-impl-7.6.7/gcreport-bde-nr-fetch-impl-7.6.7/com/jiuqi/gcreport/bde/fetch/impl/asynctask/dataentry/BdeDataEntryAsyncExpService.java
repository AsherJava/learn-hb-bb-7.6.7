/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.impl.AsyncTaskImpl
 *  com.jiuqi.nr.dataentry.service.IAsyncExpService
 */
package com.jiuqi.gcreport.bde.fetch.impl.asynctask.dataentry;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.impl.AsyncTaskImpl;
import com.jiuqi.nr.dataentry.service.IAsyncExpService;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BdeDataEntryAsyncExpService
implements IAsyncExpService {
    public List<AsyncTask> getExpAsyncTasks(String taskKey) {
        AsyncTaskImpl asyncTask = new AsyncTaskImpl();
        asyncTask.setTaskPoolType("ASYNCTASK_BDE");
        return Collections.singletonList(asyncTask);
    }
}

