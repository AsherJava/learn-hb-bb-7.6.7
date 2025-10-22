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
import nr.midstore.core.definition.bean.MidstoreParam;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstoreExcuteGetService {
    public MidstoreResultObject excuteGetData(String var1, AsyncTaskMonitor var2) throws MidstoreException;

    public MidstoreResultObject excuteGetDataByUser(String var1, String var2, AsyncTaskMonitor var3) throws MidstoreException;

    public MidstoreResultObject excuteGetData(MidstoreParam var1, AsyncTaskMonitor var2) throws MidstoreException;

    public MidstoreResultObject excuteGetData(String var1, Map<DimensionValueSet, List<String>> var2, boolean var3, AsyncTaskMonitor var4) throws MidstoreException;

    public MidstoreResultObject excuteGetDataByCode(String var1, Map<DimensionValueSet, List<String>> var2, AsyncTaskMonitor var3) throws MidstoreException;

    public MidstoreResultObject excuteGetDataByCode(String var1, Map<DimensionValueSet, List<String>> var2, boolean var3, AsyncTaskMonitor var4) throws MidstoreException;
}

