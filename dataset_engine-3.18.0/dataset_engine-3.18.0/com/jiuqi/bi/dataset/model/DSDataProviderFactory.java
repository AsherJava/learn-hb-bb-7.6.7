/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;

public abstract class DSDataProviderFactory {
    public abstract IDataSetProvider createDataSetProvider(DSModel var1);

    public IPageDataSetProvider createPageDataSetProvider(DSModel model) {
        return null;
    }

    public IPageDataSetProvider createGroupDataSetProvider(DSModel model, String groupFieldName) {
        return null;
    }

    public abstract String getType();
}

