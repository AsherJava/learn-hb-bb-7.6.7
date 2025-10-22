/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.definition.upgrade.face;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.upgrade.face.UpgradeType;

public interface ITaskVersionUpgrade {
    public String getModuleName();

    public boolean apply(String var1, UpgradeType var2);

    public void doUpgrade(String var1, UpgradeType var2, AsyncTaskMonitor var3);
}

