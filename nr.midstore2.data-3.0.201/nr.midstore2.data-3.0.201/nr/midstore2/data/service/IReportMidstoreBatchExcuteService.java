/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 */
package nr.midstore2.data.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import java.util.List;
import java.util.Map;

public interface IReportMidstoreBatchExcuteService {
    public MidstoreResultObject batchExcuteDataGet(String var1, List<String> var2, List<String> var3, Map<String, DimensionValue> var4, boolean var5, AsyncTaskMonitor var6) throws MidstoreException;

    public List<MidstoreResultObject> batchExcuteDataGets(List<String> var1, List<String> var2, List<String> var3, Map<String, DimensionValue> var4, boolean var5, AsyncTaskMonitor var6) throws MidstoreException;

    public MidstoreResultObject batchExcuteDataPost(String var1, List<String> var2, List<String> var3, Map<String, DimensionValue> var4, AsyncTaskMonitor var5) throws MidstoreException;

    public List<MidstoreResultObject> batchExcuteDataPosts(List<String> var1, List<String> var2, List<String> var3, Map<String, DimensionValue> var4, AsyncTaskMonitor var5) throws MidstoreException;
}

