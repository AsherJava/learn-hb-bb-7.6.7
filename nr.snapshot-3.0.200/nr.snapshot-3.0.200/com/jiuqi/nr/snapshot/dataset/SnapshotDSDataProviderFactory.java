/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSDataProviderFactory
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 */
package com.jiuqi.nr.snapshot.dataset;

import com.jiuqi.bi.dataset.model.DSDataProviderFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDSModel;
import com.jiuqi.nr.snapshot.dataset.provider.SnapshotDSProvider;
import com.jiuqi.nr.snapshot.dataset.query.SnapshotQueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnapshotDSDataProviderFactory
extends DSDataProviderFactory {
    @Autowired
    private SnapshotQueryExecutor snapshotQueryExecutor;

    public IDataSetProvider createDataSetProvider(DSModel dsModel) {
        return new SnapshotDSProvider(this.snapshotQueryExecutor, (SnapshotDSModel)dsModel);
    }

    public String getType() {
        return "SnapshotDataSet";
    }
}

