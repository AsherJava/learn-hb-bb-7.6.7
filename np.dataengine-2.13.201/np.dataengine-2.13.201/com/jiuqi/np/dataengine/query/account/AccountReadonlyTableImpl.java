/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.account.AccountDataRowImpl;
import java.util.ArrayList;

public class AccountReadonlyTableImpl
extends ReadonlyTableImpl {
    public AccountReadonlyTableImpl(QueryContext qCntext, DimensionValueSet masterKeys, int columnCount) {
        super(qCntext, masterKeys, columnCount);
    }

    @Override
    public DataRowImpl addDataRow(DimensionValueSet rowKeys) {
        if (this.rowKeySearch == null) {
            this.buildRowKeySearch();
        }
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        AccountDataRowImpl dataRowImpl = new AccountDataRowImpl(this, rowKeys, rowDatas);
        this.dataRows.add(dataRowImpl);
        this.rowKeySearch.put(rowKeys.toString(), dataRowImpl);
        return dataRowImpl;
    }
}

