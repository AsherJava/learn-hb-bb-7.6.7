/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter;
import com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;

public interface IDataTableFactory {
    public IDataTableMetaDataProvider getMetaDataProvider();

    public IDataTableQueryExecutor createQueryExecuter(String var1) throws DataTableAdaptException;

    public AbstractDataTableAdapter getDataTableAdapter();

    public String getTitle();

    public String getType();
}

