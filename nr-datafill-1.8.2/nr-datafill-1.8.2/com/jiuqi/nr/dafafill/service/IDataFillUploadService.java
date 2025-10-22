/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dafafill.model.AsyncUploadInfo;
import java.io.File;

public interface IDataFillUploadService {
    public boolean accept(String var1);

    public void upload(AsyncTaskMonitor var1, AsyncUploadInfo var2, File var3);
}

