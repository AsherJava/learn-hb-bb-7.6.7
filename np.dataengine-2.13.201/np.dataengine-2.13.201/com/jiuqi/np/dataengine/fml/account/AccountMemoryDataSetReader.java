/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.fml.account;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.fml.account.AccountMemoryRowData;
import com.jiuqi.np.dataengine.fml.account.AccountQueryFieldInfo;
import com.jiuqi.np.dataengine.fml.account.AccountQueryModel;
import com.jiuqi.np.dataengine.fml.account.AccountUpdateDatas;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountMemoryDataSetReader
extends MemoryDataSetReader {
    private List<IDataTable> dataTables = new ArrayList<IDataTable>();

    public AccountMemoryDataSetReader(QueryContext queryContext) {
        super(queryContext);
    }

    @Override
    public AccountQueryFieldInfo putIndex(DataModelDefinitionsCache cache, QueryField queryField, int index) {
        ColumnModelDefine fieldDefine = cache.findField(queryField.getUID());
        AccountQueryFieldInfo queryFieldInfo = new AccountQueryFieldInfo(queryField, fieldDefine, index);
        Map<QueryField, QueryFieldInfo> fieldInfoMap = this.getFieldInfoSeach();
        fieldInfoMap.put(queryField, queryFieldInfo);
        queryFieldInfo.memoryIndex = this.columns.size();
        this.columns.add(queryFieldInfo);
        this.columnCount = this.columns.size();
        return queryFieldInfo;
    }

    public void loadMainTable(QueryContext qContext, IDataTable dataTable, AccountQueryModel queryModel) {
        AccountUpdateDatas updateDatas = (AccountUpdateDatas)qContext.getUpdateDatas();
        updateDatas.getDataTables().add(dataTable);
        for (int i = 0; i < dataTable.getCount(); ++i) {
            IDataRow dataRow = dataTable.getItem(i);
            DimensionValueSet rowKey = dataRow.getRowKeys();
            AccountMemoryRowData row = this.getRow(rowKey);
            row.getDataRows()[0] = (DataRowImpl)dataRow;
            for (AccountQueryFieldInfo info : queryModel.getFieldInfos()) {
                AbstractData dataValue = dataRow.getValue(info.getColumnIndex());
                row.getDatas()[info.memoryIndex] = dataValue.getAsObject();
            }
        }
    }

    public void loadLeftJoinTable(QueryContext qContext, IDataTable dataTable, AccountQueryModel queryModel) {
        block8: {
            AccountUpdateDatas updateDatas = (AccountUpdateDatas)qContext.getUpdateDatas();
            updateDatas.getDataTables().add(dataTable);
            if (dataTable.getCount() <= 0) break block8;
            IDataRow anyRow = dataTable.getItem(0);
            DimensionValueSet anyRowKey = anyRow.getRowKeys();
            if (anyRowKey.size() == 1) {
                String leftJoinDimName = anyRowKey.getName(0);
                HashMap<Object, Integer> leftJoinRowMap = new HashMap<Object, Integer>();
                for (int i = 0; i < dataTable.getCount(); ++i) {
                    IDataRow row = dataTable.getItem(0);
                    Object leftJoinValue = row.getRowKeys().getValue(leftJoinDimName);
                    leftJoinRowMap.put(leftJoinValue, i);
                }
                for (Map.Entry entry : this.rowMap.entrySet()) {
                    Integer rowIndex;
                    DimensionValueSet mainRowKey = (DimensionValueSet)entry.getKey();
                    Object dimValue = mainRowKey.getValue(leftJoinDimName);
                    if (dimValue == null || (rowIndex = (Integer)leftJoinRowMap.get(dimValue)) == null) continue;
                    IDataRow dataRow = dataTable.getItem(rowIndex);
                    AccountMemoryRowData row = (AccountMemoryRowData)entry.getValue();
                    row.getDataRows()[queryModel.getQueryIndex()] = (DataRowImpl)dataRow;
                    for (AccountQueryFieldInfo info : queryModel.getFieldInfos()) {
                        AbstractData dataValue = dataRow.getValue(info.getColumnIndex());
                        row.getDatas()[info.memoryIndex] = dataValue.getAsObject();
                    }
                }
            } else {
                for (int i = 0; i < dataTable.getCount(); ++i) {
                    IDataRow dataRow = dataTable.getItem(i);
                    DimensionValueSet rowKey = dataRow.getRowKeys();
                    for (Map.Entry entry : this.rowMap.entrySet()) {
                        DimensionValueSet mainRowKey = (DimensionValueSet)entry.getKey();
                        AccountMemoryRowData row = (AccountMemoryRowData)entry.getValue();
                        if (!mainRowKey.isSubsetOf(rowKey)) continue;
                        row.getDataRows()[queryModel.getQueryIndex()] = (DataRowImpl)dataRow;
                        for (AccountQueryFieldInfo info : queryModel.getFieldInfos()) {
                            AbstractData dataValue = dataRow.getValue(info.getColumnIndex());
                            row.getDatas()[info.memoryIndex] = dataValue.getAsObject();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadLeftJoinDatas(QueryContext qContext, QuerySqlBuilder builder, boolean asFullJoin) throws Exception {
    }

    public AccountMemoryRowData getRow(DimensionValueSet rowKey) {
        AccountMemoryRowData row = (AccountMemoryRowData)this.rowMap.get(rowKey);
        if (row == null) {
            row = new AccountMemoryRowData(rowKey, this.columnCount, this);
            row.setGroup_flag(-1);
            this.rowMap.put(rowKey, row);
        }
        return row;
    }

    @Override
    public void reset() {
        super.reset();
        this.dataTables.clear();
    }

    @Override
    public void setDataSet(Object dataSet) {
    }

    public List<IDataTable> getDataTables() {
        return this.dataTables;
    }
}

