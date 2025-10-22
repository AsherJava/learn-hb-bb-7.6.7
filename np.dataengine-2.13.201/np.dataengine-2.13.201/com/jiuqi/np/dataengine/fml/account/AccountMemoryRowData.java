/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.np.dataengine.fml.account;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.fml.account.AccountMemoryDataSetReader;
import com.jiuqi.np.dataengine.fml.account.AccountQueryFieldInfo;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.MemoryRowData;

public class AccountMemoryRowData
extends MemoryRowData {
    private AccountMemoryDataSetReader ownner;
    private DataRowImpl[] dataRows;

    public AccountMemoryRowData(DimensionValueSet rowKey, int columnCount, AccountMemoryDataSetReader ownner) {
        super(rowKey, columnCount);
        this.ownner = ownner;
        this.dataRows = new DataRowImpl[ownner.getDataTables().size()];
    }

    public void setValue(QueryContext qContext, QueryField queryField, Object value, Object oldValue) {
        AccountQueryFieldInfo queryFieldInfo = (AccountQueryFieldInfo)this.ownner.findQueryFieldInfo(queryField);
        DataRowImpl dataRow = this.dataRows[queryFieldInfo.getQueryIndex()];
        if (dataRow == null) {
            try {
                this.dataRows[queryFieldInfo.getQueryIndex()] = dataRow = (DataRowImpl)this.ownner.getDataTables().get(queryFieldInfo.getQueryIndex()).appendRow(this.getRowKey());
            }
            catch (IncorrectQueryException e) {
                qContext.getMonitor().exception(e);
            }
        }
        if (this.isModified(queryField.getDataType(), value, oldValue)) {
            dataRow.setValue(queryFieldInfo.getColumnIndex(), value);
        }
    }

    private boolean isModified(int dataType, Object value, Object oldValue) {
        try {
            return DataType.compare((int)dataType, (Object)value, (Object)oldValue) != 0;
        }
        catch (SyntaxException e) {
            return true;
        }
    }

    public DataRowImpl[] getDataRows() {
        return this.dataRows;
    }
}

