/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.data.datain.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;

public interface ITaskFileImportEntityService {
    public String getType();

    public void importSingleEnityData(TaskDataContext var1, String var2, AsyncTaskMonitor var3) throws SingleDataException;
}

