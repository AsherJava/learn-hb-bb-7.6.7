/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.embedded;

import com.jiuqi.bi.dataset.embedded.EmbeddedIDataSetProvider;
import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;

public class EmbeddedDSDataProviderFactory
extends DSDataProviderFactory {
    @Override
    public IDataSetProvider createDataSetProvider(DSModel model) {
        return new EmbeddedIDataSetProvider();
    }

    @Override
    public String getType() {
        return "embedded";
    }
}

