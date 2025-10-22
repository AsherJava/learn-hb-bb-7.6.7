/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import java.io.File;

public interface IUploadTypeService {
    public ImportResultObject upload(File var1, UploadParam var2, AsyncTaskMonitor var3);
}

