/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.bi.dataset.sql.SQLDataSetProvider;
import com.jiuqi.bi.dataset.sql.SQLGroupPageDataSetProvider;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.dataset.sql.SQLPageDataSetProvider;

public class SQLDSDataProviderFactory
extends DSDataProviderFactory {
    @Override
    public IDataSetProvider createDataSetProvider(DSModel model) {
        return new SQLDataSetProvider((SQLModel)model);
    }

    @Override
    public IPageDataSetProvider createPageDataSetProvider(DSModel model) {
        return new SQLPageDataSetProvider((SQLModel)model);
    }

    @Override
    public IPageDataSetProvider createGroupDataSetProvider(DSModel model, String groupFieldName) {
        return new SQLGroupPageDataSetProvider(model, groupFieldName);
    }

    @Override
    public String getType() {
        return "com.jiuqi.bi.dataset.sq";
    }
}

