/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 */
package com.jiuqi.nr.snapshot.dataset.provider;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDSModel;
import com.jiuqi.nr.snapshot.dataset.query.SnapshotQueryExecutor;

public class SnapshotDSProvider
implements IDataSetProvider {
    private SnapshotQueryExecutor snapshotQueryExecutor;
    private SnapshotDSModel snapshotDSModel;

    public SnapshotDSProvider(SnapshotQueryExecutor snapshotQueryExecutor, SnapshotDSModel snapshotDSModel) {
        this.snapshotQueryExecutor = snapshotQueryExecutor;
        this.snapshotDSModel = snapshotDSModel;
    }

    public void open(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, IDSContext idsContext) throws BIDataSetException {
        try {
            this.snapshotQueryExecutor.buildDataSet(memoryDataSet, idsContext, this.snapshotDSModel);
        }
        catch (Exception e) {
            throw new BIDataSetException(e.getMessage(), (Throwable)e);
        }
    }
}

