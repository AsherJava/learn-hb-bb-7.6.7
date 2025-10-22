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
package com.jiuqi.bi.dataset.calibersum;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.calibersum.CaliberSumQueryExecutor;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;

public class CaliberSumDSProvider
implements IDataSetProvider {
    private CaliberSumQueryExecutor caliberSumExecutor;
    private CaliberSumDSModel dsModel;

    public CaliberSumDSProvider(CaliberSumQueryExecutor caliberSumExecutor, CaliberSumDSModel dsModel) {
        this.caliberSumExecutor = caliberSumExecutor;
        this.dsModel = dsModel;
    }

    public void open(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, IDSContext dsContext) throws BIDataSetException {
        try {
            this.caliberSumExecutor.runQuery(this.dsModel, dsContext, 0, 1, memoryDataSet);
            if (DataEngineConsts.DATA_ENGINE_DEBUG) {
                System.out.println(memoryDataSet.toString());
            }
        }
        catch (Exception e) {
            throw new BIDataSetException(e.getMessage(), (Throwable)e);
        }
    }
}

