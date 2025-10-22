/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.nvwa.midstore.MidstoreExeContext
 *  com.jiuqi.nvwa.midstore.MidstoreExecutionException
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject
 */
package nr.midstore2.data.work;

import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.nvwa.midstore.MidstoreExeContext;
import com.jiuqi.nvwa.midstore.MidstoreExecutionException;
import com.jiuqi.nvwa.midstore.core.definition.bean.MidstoreResultObject;

public interface IReportMidstoreExcuteGetService {
    public MidstoreResultObject readFieldDataFromMidstore(MidstoreExeContext var1, IDataExchangeTask var2) throws MidstoreExecutionException;
}

