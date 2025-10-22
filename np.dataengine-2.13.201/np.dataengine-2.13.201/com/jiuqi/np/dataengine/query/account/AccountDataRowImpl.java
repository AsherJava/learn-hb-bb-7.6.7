/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountDataRowImpl
extends DataRowImpl {
    private boolean existTimeData = true;
    private static final Logger logger = LoggerFactory.getLogger(AccountDataRowImpl.class);

    public AccountDataRowImpl(ReadonlyTableImpl table, DimensionValueSet rowKeys, ArrayList<Object> rowDatas) {
        super(table, rowKeys, rowDatas);
    }

    public void setExistTimeData(boolean existTimeData) {
        this.existTimeData = existTimeData;
    }

    public boolean isExistTimeData() {
        return this.existTimeData;
    }
}

