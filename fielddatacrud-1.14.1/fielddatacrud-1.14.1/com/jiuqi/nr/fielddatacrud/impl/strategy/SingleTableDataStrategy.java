/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataRowReader
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.impl.DataValue
 *  com.jiuqi.nr.datacrud.impl.RowData
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.fielddatacrud.impl.strategy;

import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataRowReader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.IFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.strategy.BaseFieldDataStrategy;
import java.util.ArrayList;
import java.util.List;

public class SingleTableDataStrategy
extends BaseFieldDataStrategy<IDataQuery>
implements IDataRowReader,
IFieldDataStrategy {
    public SingleTableDataStrategy(FieldDataStrategyFactory factory) {
        super(factory);
    }

    @Override
    protected IDataQuery getDataQuery(IFieldQueryInfo queryInfo, ParamRelation paramRelation) {
        return this.dataEngineService.getDataQuery();
    }

    @Override
    protected void execQuery() {
        try {
            ((IDataQuery)this.dataQuery).queryToDataRowReader(this.context, (IDataRowReader)this);
        }
        catch (Exception e) {
            throw new CrudException(4101, "\u6570\u636e\u5f15\u64ce\u53d6\u6570\u9519\u8bef", (Throwable)e);
        }
    }

    public boolean needRowKey() {
        return true;
    }

    public void start(QueryContext qContext, IFieldsInfo fieldsInfo) {
        List<IMetaData> metaData = this.relation.getMetaData();
        this.dataReader.start(new ArrayList<IMetaData>(metaData), -1L);
    }

    public void readRowData(QueryContext qContext, IDataRow row) {
        List<IMetaData> metaData = this.relation.getMetaData();
        RowData rowData = new RowData();
        ArrayList<DataValue> values = new ArrayList<DataValue>(metaData.size() + 1);
        rowData.setDataValues(values);
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(row.getRowKeys());
        rowData.setDimension(builder.getCombination());
        List<TableDimSet> tableDim = this.relation.getTableDim(metaData);
        TableDimSet tableDimSet = tableDim.get(0);
        DimensionCombinationBuilder masterKeyBuilder = new DimensionCombinationBuilder();
        ENameSet masterKeyName = tableDimSet.getMasterKeyName();
        for (int i = 0; i < masterKeyName.size(); ++i) {
            String name = masterKeyName.get(i);
            Object value = row.getRowKeys().getValue(name);
            masterKeyBuilder.setValue(name, null, value);
        }
        rowData.setMasterDimension(masterKeyBuilder.getCombination());
        for (IMetaData meta : metaData) {
            AbstractData abstractData = this.metaDataTransfer(meta, row, rowData);
            DataValue value = new DataValue(meta, abstractData);
            value.setRowData(rowData);
            values.add(value);
        }
        this.dataReader.readRow((IRowData)rowData);
    }

    public void finish(QueryContext qContext) {
        this.dataReader.finish();
    }
}

