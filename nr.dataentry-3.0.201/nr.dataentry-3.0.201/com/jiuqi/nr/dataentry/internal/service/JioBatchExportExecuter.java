/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import java.util.List;

public interface JioBatchExportExecuter {
    public ExportData export(BatchExportInfo var1, AsyncTaskMonitor var2, BatchDimensionParam var3, List<String> var4, List<String> var5, List<String> var6, String var7, List<BatchExportData> var8) throws Exception;

    public ExportData exportOfMultiplePeriod(BatchExportInfo var1, AsyncTaskMonitor var2, List<BatchDimensionParam> var3, List<String> var4, List<String> var5, String var6, List<BatchExportData> var7) throws Exception;
}

