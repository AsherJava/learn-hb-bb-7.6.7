/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 */
package nr.midstore2.data.work.floating;

import com.jiuqi.bi.core.midstore.dataexchange.model.DETableModel;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstorePostFloatDataService {
    public void writeFloatFieldDataToMidstore(ReportMidstoreContext var1, IDataExchangeTask var2, DETableModel var3) throws MidstoreException;
}

