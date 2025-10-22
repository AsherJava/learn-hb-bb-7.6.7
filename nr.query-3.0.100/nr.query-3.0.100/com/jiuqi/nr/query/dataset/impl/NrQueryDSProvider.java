/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.IDataSetProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 */
package com.jiuqi.nr.query.dataset.impl;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.nr.query.dataset.QueryDSModel;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSExecutor;

public class NrQueryDSProvider
implements IDataSetProvider {
    private QueryDSModel dsModel;
    private NrQueryDSExecutor nrQueryDSExecutor;

    public NrQueryDSProvider(NrQueryDSExecutor nrQueryDSExecutor, QueryDSModel dsModel) {
        this.nrQueryDSExecutor = nrQueryDSExecutor;
        this.dsModel = dsModel;
    }

    public void open(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, IDSContext dsContext) throws BIDataSetException {
        try {
            this.nrQueryDSExecutor.runQuery(this.dsModel, dsContext, 0, 1, memoryDataSet);
            if (DataEngineConsts.DATA_ENGINE_DEBUG) {
                System.out.println(memoryDataSet.toString());
            }
        }
        catch (Exception e) {
            throw new BIDataSetException(e.getMessage(), (Throwable)e);
        }
    }
}

