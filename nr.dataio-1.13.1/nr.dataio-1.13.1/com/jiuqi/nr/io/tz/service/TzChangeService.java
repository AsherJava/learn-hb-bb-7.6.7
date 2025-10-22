/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.io.tz.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.FlagState;

public interface TzChangeService {
    public void flagAfter(TzParams var1, FlagState var2, AsyncTaskMonitor var3);

    public void saveAfter(TzParams var1, FlagState var2, AsyncTaskMonitor var3);
}

