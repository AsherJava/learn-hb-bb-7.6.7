/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 *  com.jiuqi.bi.dataset.model.IPageDataSetProvider
 */
package com.jiuqi.nr.zbquery.dataset;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModel;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSProvider;
import com.jiuqi.nr.zbquery.dataset.ZBQueryPageDSProvider;

public class ZBQueryDSDataProviderFactory
extends DSDataProviderFactory {
    public String getType() {
        return "com.jiuqi.nr.dataset.zbquery";
    }

    public IPageDataSetProvider createPageDataSetProvider(DSModel dsModel) {
        return new ZBQueryPageDSProvider((ZBQueryDSModel)dsModel);
    }

    public IDataSetProvider createDataSetProvider(DSModel dsModel) {
        return new ZBQueryDSProvider((ZBQueryDSModel)dsModel);
    }
}

