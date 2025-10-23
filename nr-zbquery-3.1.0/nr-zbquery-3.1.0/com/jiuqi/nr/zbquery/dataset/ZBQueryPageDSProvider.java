/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.IPageDataSetProvider
 *  com.jiuqi.bi.query.result.ColumnInfo
 */
package com.jiuqi.nr.zbquery.dataset;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSDefine;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModel;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSProvider;
import com.jiuqi.nr.zbquery.engine.executor.QueryExecutor;
import com.jiuqi.nr.zbquery.model.PageInfo;
import java.util.UUID;
import org.springframework.util.ObjectUtils;

public class ZBQueryPageDSProvider
implements IPageDataSetProvider {
    private ZBQueryDSModel dsModel;
    private int pageSize;

    public ZBQueryPageDSProvider(ZBQueryDSModel dsModel) {
        this.dsModel = dsModel;
    }

    public int open(MemoryDataSet<BIDataSetFieldInfo> dataSet, int pageNum, IDSContext context) throws BIDataSetException {
        if (pageNum < 1) {
            throw new IllegalArgumentException("pageNum : " + pageNum);
        }
        ZBQueryDSDefine zbQueryDSDefine = this.dsModel.getZbQueryDSDefine();
        if (!ObjectUtils.isEmpty(zbQueryDSDefine)) {
            try {
                QueryExecutor queryExecutor = new QueryExecutor(UUID.randomUUID().toString(), zbQueryDSDefine.getZbQueryModel());
                PageInfo pageInfo = new PageInfo(this.pageSize, pageNum);
                MemoryDataSet<ColumnInfo> memoryDataSet = queryExecutor.query(ZBQueryDSProvider.generateConditionValues(context), pageInfo);
                for (int i = 0; i < memoryDataSet.size(); ++i) {
                    dataSet.add(memoryDataSet.getBuffer(i));
                }
                ZBQueryDSProvider.setFixRefDims(dataSet, this.dsModel, queryExecutor);
            }
            catch (Exception e) {
                throw new BIDataSetException(e.getMessage(), (Throwable)e);
            }
        }
        return -1;
    }

    public int getPageCount(IDSContext context) throws BIDataSetException {
        int allSize = this.getRecordCount(context);
        return (allSize - 1) / this.pageSize + 1;
    }

    public int getRecordCount(IDSContext context) throws BIDataSetException {
        MemoryDataSet<ColumnInfo> memoryDataSet = new MemoryDataSet<ColumnInfo>();
        ZBQueryDSDefine zbQueryDSDefine = this.dsModel.getZbQueryDSDefine();
        if (!ObjectUtils.isEmpty(zbQueryDSDefine)) {
            QueryExecutor queryExecutor = new QueryExecutor(UUID.randomUUID().toString(), zbQueryDSDefine.getZbQueryModel());
            try {
                memoryDataSet = queryExecutor.query(ZBQueryDSProvider.generateConditionValues(context), null);
            }
            catch (Exception e) {
                throw new BIDataSetException(e.getMessage(), (Throwable)e);
            }
        }
        return memoryDataSet.size();
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize : " + pageSize);
        }
        this.pageSize = pageSize;
    }
}

