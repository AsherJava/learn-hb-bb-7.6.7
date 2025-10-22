/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.client.service.export;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import nr.single.map.data.TaskDataContext;

public interface IExportDataCheckResult {
    public void LoadNetCheckDataToCache(TaskDataContext var1, String var2, Map<String, DimensionValue> var3, String var4) throws Exception;

    public void exportCheckDataFromCache(TaskDataContext var1, String var2, String var3) throws Exception;

    public void exportCheckData(TaskDataContext var1, String var2, Map<String, DimensionValue> var3, String var4) throws Exception;
}

