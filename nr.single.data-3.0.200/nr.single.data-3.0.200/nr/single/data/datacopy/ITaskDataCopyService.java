/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.data.datacopy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.single.data.bean.TaskCopyContext;
import nr.single.data.bean.TaskCopyParam;

public interface ITaskDataCopyService {
    public String copyDataByParam(TaskCopyParam var1, AsyncTaskMonitor var2) throws Exception;

    public String copyDataByTask(TaskCopyContext var1, String var2, String var3, String var4, String var5, String var6, String var7, AsyncTaskMonitor var8) throws Exception;

    public String copyDataByFormScheme(TaskCopyContext var1, String var2, String var3, String var4, String var5, String var6, String var7, AsyncTaskMonitor var8) throws Exception;
}

