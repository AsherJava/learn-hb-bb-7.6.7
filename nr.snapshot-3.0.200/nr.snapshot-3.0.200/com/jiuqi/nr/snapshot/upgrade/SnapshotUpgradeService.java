/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.snapshot.upgrade;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.snapshot.input.UpgradeContext;
import com.jiuqi.nr.snapshot.output.TaskObj;
import java.util.List;

public interface SnapshotUpgradeService {
    public List<TaskObj> getUpgradeTasks();

    public void doUpgrade(UpgradeContext var1, AsyncTaskMonitor var2);
}

