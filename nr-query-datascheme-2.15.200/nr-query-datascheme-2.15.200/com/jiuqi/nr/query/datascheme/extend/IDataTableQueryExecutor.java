/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.common.QueryField
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataQueryParam;
import java.util.List;

public interface IDataTableQueryExecutor {
    public List<String> getParamNames() throws DataTableAdaptException;

    public MemoryDataSet<QueryField> execute(DataQueryParam var1) throws DataTableAdaptException;

    default public IConnectionProvider getConnectionProvider() {
        return null;
    }
}

