/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.ProgressException
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.bi.monitor.ProgressException;
import com.jiuqi.np.asynctask.TaskResultEnum;
import com.jiuqi.nr.common.asynctask.entity.SubJobMessageInfo;
import java.util.List;

public interface AsyncTaskStepMonitor {
    public void startTask(String var1, int var2);

    public void startTask(String var1, int var2, String var3);

    public void startTask(String var1, int[] var2);

    public void startTask(String var1, int[] var2, String var3);

    public void stepIn(String var1);

    public void finishTask(String var1, String var2, String var3) throws ProgressException;

    public void finishTask(String var1, String var2, String var3, TaskResultEnum var4) throws ProgressException;

    public boolean isCanceled();

    public void cancel(String var1, String var2);

    public void waitParallelTask(List<String> var1);

    public void waitAndUpdateParallelTaskMessage(List<SubJobMessageInfo> var1);
}

