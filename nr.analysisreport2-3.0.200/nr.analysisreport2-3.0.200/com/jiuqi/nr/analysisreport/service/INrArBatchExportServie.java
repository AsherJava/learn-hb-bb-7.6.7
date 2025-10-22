/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.analysisreport.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.analysisreport.vo.ReportExportVO;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;

public interface INrArBatchExportServie {
    public void batchExport(ReportExportVO var1, AsyncTaskMonitor var2) throws Exception;

    public IEntityTable buildEntityTable(ReportExportVO var1) throws Exception;
}

