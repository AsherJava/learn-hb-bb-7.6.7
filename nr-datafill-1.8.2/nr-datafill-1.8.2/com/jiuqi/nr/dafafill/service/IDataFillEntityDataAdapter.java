/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.nr.dafafill.model.DataFillEntityDataBase;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import java.util.List;

public interface IDataFillEntityDataAdapter {
    public boolean accept(QueryField var1);

    public List<DataFillEntityDataBase> query(DataFillEntityDataQueryInfo var1);

    public DataFillEntityDataBase queryByIdOrCode(DataFillEntityDataQueryInfo var1);
}

