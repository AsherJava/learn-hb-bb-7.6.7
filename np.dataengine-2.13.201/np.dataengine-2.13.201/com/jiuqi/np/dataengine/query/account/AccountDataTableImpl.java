/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.TableChangesCommitter;
import com.jiuqi.np.dataengine.query.account.AccountDataRowImpl;
import com.jiuqi.np.dataengine.query.account.AccountTableChangesCommitter;
import com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountDataTableImpl
extends DataTableImpl {
    private static final Logger logger = LoggerFactory.getLogger(AccountDataTableImpl.class);
    private final IAccountColumnModelFinder accountColumnModelFinder = (IAccountColumnModelFinder)SpringBeanProvider.getBean(IAccountColumnModelFinder.class);

    public AccountDataTableImpl(QueryContext qContext, DimensionValueSet masterKeys, int columnCount) {
        super(qContext, masterKeys, columnCount);
    }

    @Override
    public DataRowImpl addDataRow(DimensionValueSet rowKeys) {
        if (this.rowKeySearch == null) {
            this.buildRowKeySearch();
        }
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        AccountDataRowImpl dataRowImpl = new AccountDataRowImpl(this, rowKeys, rowDatas);
        this.dataRows.add(dataRowImpl);
        this.rowKeySearch.put(rowKeys.toString(), dataRowImpl);
        return dataRowImpl;
    }

    @Override
    public ArrayList<TableChangesCommitter> createCommitters(Connection connection, HashMap<QueryTable, HashMap<QueryField, List<Integer>>> queryRegion, UpdateDataSet updateDataSet, Date queryStartVersionDate, Date queryVersionDate) {
        ArrayList<TableChangesCommitter> committers = new ArrayList<TableChangesCommitter>();
        DataModelDefinitionsCache dataDefinitionsCache = this.cache.getDataModelDefinitionsCache();
        HashMap<QueryTable, HashMap<QueryField, List<Integer>>> accountQueryRegion = new HashMap<QueryTable, HashMap<QueryField, List<Integer>>>();
        HashMap<String, QueryTable> tableNameMap = new HashMap<String, QueryTable>();
        for (Map.Entry<QueryTable, HashMap<QueryField, List<Integer>>> regionPair : queryRegion.entrySet()) {
            String accountTableName;
            QueryTable queryTable = regionPair.getKey();
            String tableName = queryTable.getTableName();
            if (!tableName.equals(accountTableName = this.accountColumnModelFinder.getAccountTableName(tableName))) continue;
            tableNameMap.put(accountTableName, queryTable);
        }
        HashMap<String, TableModelRunInfo> tableModelRunInfoMap = new HashMap<String, TableModelRunInfo>();
        for (Map.Entry<QueryTable, HashMap<QueryField, List<Integer>>> entry : queryRegion.entrySet()) {
            QueryTable queryTable = entry.getKey();
            HashMap<QueryField, List<Integer>> queryFields = entry.getValue();
            String tableName = queryTable.getTableName();
            String accountTableName = this.accountColumnModelFinder.getAccountTableName(tableName);
            QueryTable accountQueryTable = (QueryTable)tableNameMap.get(accountTableName);
            if (accountQueryRegion.containsKey(accountQueryTable)) {
                HashMap accountQueryFields = (HashMap)accountQueryRegion.get(accountQueryTable);
                accountQueryFields.putAll(queryFields);
            } else {
                accountQueryRegion.put(accountQueryTable, queryFields);
            }
            tableModelRunInfoMap.put(tableName, dataDefinitionsCache.getTableInfo(tableName));
        }
        for (Map.Entry<QueryTable, HashMap<QueryField, List<Integer>>> entry : accountQueryRegion.entrySet()) {
            TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfo(entry.getKey().getTableName());
            AccountTableChangesCommitter commiter = new AccountTableChangesCommitter(this.qContext, entry.getKey(), tableModelRunInfoMap, entry.getValue(), tableRunInfo, this.fieldsInfoImpl, this.systemFieldsInfo, connection, this.designTimeData, this.masterKeys, this.needCheckKeys, this.queryParam);
            commiter.setUpdateDataTable(updateDataSet);
            committers.add(commiter);
        }
        return committers;
    }

    @Override
    public IDataRow findRow(DimensionValueSet rowKeys) {
        IDataRow dataRow;
        if (this.rowKeySearch == null) {
            this.buildRowKeySearch();
        }
        if ((dataRow = (IDataRow)this.rowKeySearch.get(rowKeys.toString())) != null) {
            return dataRow;
        }
        dataRow = (IDataRow)this.rowKeySearch.get((rowKeys = this.rebuildRowKey(rowKeys)).toString());
        if (dataRow != null) {
            return dataRow;
        }
        DimensionValueSet copyValue = new DimensionValueSet(rowKeys);
        return (IDataRow)this.rowKeySearch.get(copyValue.toString());
    }

    private DimensionValueSet rebuildRowKey(DimensionValueSet rowKeys) {
        DimensionValueSet rowKey = new DimensionValueSet();
        Optional firstRowKey = this.rowKeySearch.keySet().stream().findFirst();
        if (firstRowKey.isPresent()) {
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.parseString((String)firstRowKey.get());
            DimensionSet dimesions = dimensionValueSet.getDimensionSet();
            for (int index = 0; index < dimesions.size(); ++index) {
                String dimName = dimesions.get(index);
                rowKey.setValue(dimName, rowKeys.getValue(dimName));
            }
        }
        return rowKey;
    }
}

