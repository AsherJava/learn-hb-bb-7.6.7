/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.single.client.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.single.client.bean.SingleExportData;
import nr.single.client.bean.SingleExportParam;

public interface ISingleExportService {
    public SingleExportData export(SingleExportParam var1, AsyncTaskMonitor var2) throws Exception;
}

