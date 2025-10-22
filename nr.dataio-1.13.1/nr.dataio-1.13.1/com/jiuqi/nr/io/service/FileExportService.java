/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.io.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.io.params.base.TableContext;
import java.util.zip.ZipOutputStream;

public interface FileExportService {
    public void getExtZipOutputStream(TableContext var1, ZipOutputStream var2, AsyncTaskMonitor var3);

    public String getExtZipFile(TableContext var1, AsyncTaskMonitor var2);
}

