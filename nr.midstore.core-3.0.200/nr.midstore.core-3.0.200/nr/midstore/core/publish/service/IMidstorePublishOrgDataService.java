/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.midstore.core.publish.service;

import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstorePublishOrgDataService {
    public void publishOrgDataFields(MidstoreContext var1, IDataExchangeTask var2, AsyncTaskMonitor var3) throws MidstoreException;
}

