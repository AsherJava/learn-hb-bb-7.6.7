/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.gcreport.webserviceclient.task;

import com.jiuqi.gcreport.webserviceclient.task.WebserviceClientExecuter;
import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class WebserviceClientAsyncTask
implements NpAsyncTaskExecutor {
    public void execute(Object args, AsyncTaskMonitor monitor) {
        try {
            WebserviceClientParam wsParam = (WebserviceClientParam)args;
            ArrayList<String> executeMessages = new ArrayList<String>();
            WebserviceClientExecuter webserviceClientExecuter = new WebserviceClientExecuter();
            webserviceClientExecuter.executeWebservicClient(wsParam, executeMessages, monitor);
            if (monitor.isCancel()) {
                String retStr = "\u4efb\u52a1\u53d6\u6d88";
                monitor.canceled(retStr, (Object)retStr);
            } else {
                monitor.finish("\u4efb\u52a1\u6267\u884c\u5b8c\u6210", executeMessages);
            }
        }
        catch (Exception e) {
            monitor.error("\u4efb\u52a1\u6267\u884c\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
        }
    }

    public String getTaskPoolType() {
        return "GC_ASYNC_TASK_WEBSERVICE_CLIENT";
    }
}

