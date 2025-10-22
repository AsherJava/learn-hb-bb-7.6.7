/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.enums.ExportType;

public interface IDataFillExportService {
    public boolean accept(ExportType var1);

    public void export(DataFillDataQueryInfo var1, AsyncTaskMonitor var2);
}

