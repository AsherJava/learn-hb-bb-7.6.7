/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 */
package com.jiuqi.nr.fielddatacrud.impl.strategy;

import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.IFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.strategy.BaseFieldDataStrategy;
import java.util.ArrayList;
import java.util.List;

public class FixTableDataStrategy
extends BaseFieldDataStrategy<IDataQuery>
implements IFieldDataStrategy {
    public FixTableDataStrategy(FieldDataStrategyFactory factory) {
        super(factory);
    }

    @Override
    protected void execQuery() {
        IReadonlyTable readonlyTable;
        try {
            readonlyTable = ((IDataQuery)this.dataQuery).executeReader(this.context);
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u67e5\u8be2\u6570\u636e\u5931\u8d25", (Throwable)e);
        }
        try {
            List<IMetaData> metaData = this.relation.getMetaData();
            this.dataReader.start(new ArrayList<IMetaData>(metaData), readonlyTable.getCount());
            for (int i = 0; i < readonlyTable.getCount(); ++i) {
                IDataRow row = readonlyTable.getItem(i);
                this.readRowData(row);
            }
            this.dataReader.finish();
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u6d41\u5f0f\u8fd4\u56de\u5931\u8d25", e);
            throw new CrudException(4101, "\u6570\u636e\u6d41\u5f0f\u8fd4\u56de\u5931\u8d25");
        }
    }

    @Override
    protected IDataQuery getDataQuery(IFieldQueryInfo queryInfo, ParamRelation paramRelation) {
        return this.dataEngineService.getDataQuery();
    }
}

