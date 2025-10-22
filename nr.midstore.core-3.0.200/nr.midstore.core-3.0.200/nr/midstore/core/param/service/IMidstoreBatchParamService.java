/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.midstore.core.param.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstoreBatchParamService {
    public MidstoreResultObject doLinkBaseDataFromFields(String var1, AsyncTaskMonitor var2) throws MidstoreException;
}

