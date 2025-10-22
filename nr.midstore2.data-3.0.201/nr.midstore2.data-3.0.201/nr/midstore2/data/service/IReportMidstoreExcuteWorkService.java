/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 */
package nr.midstore2.data.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import java.util.List;
import java.util.Map;

public interface IReportMidstoreExcuteWorkService {
    public List<MidstoreResultObject> excuteDataGetByCodes(String var1, String var2, Map<DimensionValueSet, List<String>> var3, boolean var4, AsyncTaskMonitor var5) throws MidstoreException;

    public List<MidstoreResultObject> excuteDataPostByCodes(String var1, String var2, Map<DimensionValueSet, List<String>> var3, AsyncTaskMonitor var4) throws MidstoreException;
}

