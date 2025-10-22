/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.service.querycheck;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.exception.SingleDataException;

public interface ISingleExportQueryCheckService {
    public void exportQueryCheckResult(TaskDataContext var1, String var2, Map<String, DimensionValue> var3, AsyncTaskMonitor var4) throws SingleDataException;
}

