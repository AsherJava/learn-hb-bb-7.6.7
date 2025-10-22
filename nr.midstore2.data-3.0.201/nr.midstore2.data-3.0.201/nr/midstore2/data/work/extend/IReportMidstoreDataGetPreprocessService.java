/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 */
package nr.midstore2.data.work.extend;

import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreDataGetPreprocessService {
    public void preprocessDataToMidstore(ReportMidstoreContext var1, IDataExchangeTask var2);
}

