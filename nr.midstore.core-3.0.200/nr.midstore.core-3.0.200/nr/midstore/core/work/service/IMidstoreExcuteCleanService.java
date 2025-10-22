/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.midstore.core.work.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import nr.midstore.core.definition.bean.MidstoreParam;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstoreExcuteCleanService {
    public MidstoreResultObject excuteCleanData(String var1, AsyncTaskMonitor var2) throws MidstoreException;

    public MidstoreResultObject excuteCleanData(MidstoreParam var1, AsyncTaskMonitor var2) throws MidstoreException;
}

