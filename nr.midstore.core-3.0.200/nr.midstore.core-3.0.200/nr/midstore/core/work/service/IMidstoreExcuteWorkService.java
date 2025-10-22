/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package nr.midstore.core.work.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstoreExcuteWorkService {
    public MidstoreResultObject excuteDataByCode(String var1, Map<DimensionValueSet, List<String>> var2, AsyncTaskMonitor var3) throws MidstoreException;
}

