/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 */
package com.jiuqi.nr.fielddatacrud.impl.strategy;

import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.IFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.strategy.BaseFieldDataStrategy;
import java.util.ArrayList;
import java.util.List;

public class TableDataStrategy
extends BaseFieldDataStrategy<IDataQuery>
implements IFieldDataStrategy {
    public TableDataStrategy(FieldDataStrategyFactory factory) {
        super(factory);
    }

    @Override
    protected void execQuery() {
        try {
            List<IMetaData> metaData = this.relation.getMetaData();
            PageInfo pageInfo = this.queryInfo.getPageInfo();
            if (pageInfo != null) {
                IReadonlyTable readonlyTable = ((IDataQuery)this.dataQuery).executeReader(this.context);
                this.dataReader.start(new ArrayList<IMetaData>(metaData), readonlyTable.getCount());
                for (int i = 0; i < readonlyTable.getCount(); ++i) {
                    IDataRow row = readonlyTable.getItem(i);
                    this.readRowData(row);
                }
            } else {
                pageInfo = new PageInfo();
                pageInfo.setPageIndex(0);
                pageInfo.setRowsPerPage(100000);
                super.addPage(pageInfo);
                IReadonlyTable readonlyTable = ((IDataQuery)this.dataQuery).executeReader(this.context);
                int totalCount = readonlyTable.getTotalCount();
                this.dataReader.start(new ArrayList<IMetaData>(metaData), totalCount);
                if (totalCount == 0) {
                    this.dataReader.finish();
                    return;
                }
                for (int i = 0; i < readonlyTable.getCount(); ++i) {
                    IDataRow row = readonlyTable.getItem(i);
                    this.readRowData(row);
                }
                int pageSize = (totalCount - 1) / pageInfo.getRowsPerPage();
                for (int page = 1; page < pageSize; ++page) {
                    pageInfo.setPageIndex(page);
                    super.addPage(pageInfo);
                    readonlyTable = ((IDataQuery)this.dataQuery).executeReader(this.context);
                    for (int i = 0; i < readonlyTable.getCount(); ++i) {
                        IDataRow row = readonlyTable.getItem(i);
                        this.readRowData(row);
                    }
                }
            }
            this.dataReader.finish();
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u67e5\u8be2\u6570\u636e\u5931\u8d25", (Throwable)e);
        }
    }

    @Override
    protected IDataQuery getDataQuery(IFieldQueryInfo queryInfo, ParamRelation paramRelation) {
        return this.dataEngineService.getDataQuery();
    }
}

