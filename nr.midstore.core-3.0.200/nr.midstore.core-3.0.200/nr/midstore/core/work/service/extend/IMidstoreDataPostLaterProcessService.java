/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 */
package nr.midstore.core.work.service.extend;

import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import nr.midstore.core.definition.bean.MidstoreContext;

public interface IMidstoreDataPostLaterProcessService {
    public void laterProcessDataToMidstore(MidstoreContext var1, IDataExchangeTask var2);
}

