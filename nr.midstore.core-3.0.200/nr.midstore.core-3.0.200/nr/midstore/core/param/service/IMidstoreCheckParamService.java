/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package nr.midstore.core.param.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.common.FormAccessType;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstoreCheckParamService {
    public MidstoreContext getContext(String var1, AsyncTaskMonitor var2);

    public MidstoreResultObject doCheckParams(String var1, AsyncTaskMonitor var2);

    public MidstoreResultObject doCheckParamsBeforePulish(MidstoreContext var1);

    public MidstoreResultObject doCheckParamsBeforeCleanData(MidstoreContext var1);

    public MidstoreResultObject doCheckParamsBeforeGetData(MidstoreContext var1);

    public MidstoreResultObject doCheckParamsBeforePostData(MidstoreContext var1);

    public MidstoreResultObject doLoadFormScheme(MidstoreContext var1, boolean var2) throws MidstoreException;

    public void tranUnitFormsToTables(MidstoreContext var1, Map<DimensionValueSet, List<String>> var2);

    public Map<DimensionValueSet, List<String>> getUnitFormKeys(MidstoreContext var1, FormAccessType var2) throws MidstoreException;
}

