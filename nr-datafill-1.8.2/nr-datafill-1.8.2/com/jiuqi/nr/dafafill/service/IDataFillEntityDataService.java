/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.nr.dafafill.model.DataFillEntityData;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataAnalysisInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataResult;
import java.util.List;

public interface IDataFillEntityDataService {
    public DataFillEntityDataResult query(DataFillEntityDataQueryInfo var1);

    public DataFillEntityData queryByIdOrCode(DataFillEntityDataQueryInfo var1);

    public List<DataFillEntityData> queryMultiValByIdOrCode(DataFillEntityDataQueryInfo var1);

    public DataFillEntityData queryByPrimaryOrSearch(DataFillEntityDataQueryInfo var1);

    public List<Object> queryByPrimaryOrSearch(DataFillEntityDataAnalysisInfo var1);
}

