/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.OrderedNodeCheckResultInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;

public interface IBatchDataSumService {
    public void batchDataSumForm(BatchDataSumInfo var1, AsyncTaskMonitor var2, float var3);

    public NodeCheckResultInfo nodeCheck(NodeCheckInfo var1);

    public NodeCheckResultInfo nodeCheck(NodeCheckInfo var1, AsyncTaskMonitor var2);

    public NodeCheckResultInfo batchNodeCheck(NodeCheckInfo var1, AsyncTaskMonitor var2);

    public NodeCheckResultInfo nodecheckResult(String var1);

    public OrderedNodeCheckResultInfo nodecheckOrderResult(String var1);

    public ExportData nodecheckExport(String var1);
}

