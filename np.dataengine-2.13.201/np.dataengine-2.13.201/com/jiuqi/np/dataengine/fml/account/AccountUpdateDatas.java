/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.np.dataengine.fml.account;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.fml.account.AccountMemoryDataSetReader;
import com.jiuqi.np.dataengine.fml.account.AccountMemoryRowData;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.UpdateDatas;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountUpdateDatas
extends UpdateDatas {
    private List<IDataTable> dataTables = new ArrayList<IDataTable>();

    @Override
    public void addValue(QueryContext qContext, QueryField field, Object value, Object oldValue) {
        AccountMemoryDataSetReader reader = (AccountMemoryDataSetReader)qContext.getDataReader();
        AccountMemoryRowData row = (AccountMemoryRowData)reader.getRowDatas();
        row.setValue(qContext, field, value, oldValue);
    }

    @Override
    public void reset() {
        this.dataTables.clear();
    }

    @Override
    public void commitData(QueryContext qContext, IMonitor monitor, QueryParam queryParam) throws ParseException, SQLException {
        if (this.dataTables.size() > 0) {
            try {
                this.dataTables.get(0).commitChanges(true, true);
            }
            catch (Exception e) {
                qContext.getMonitor().exception(e);
            }
        }
    }

    public List<IDataTable> getDataTables() {
        return this.dataTables;
    }
}

