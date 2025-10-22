/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;

public interface IDataRowReader {
    default public boolean needRowKey() {
        return false;
    }

    public void start(QueryContext var1, IFieldsInfo var2) throws Exception;

    public void readRowData(QueryContext var1, IDataRow var2) throws Exception;

    public void finish(QueryContext var1) throws Exception;
}

