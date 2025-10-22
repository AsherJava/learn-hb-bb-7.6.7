/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.fml.account;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.fml.account.AccountMemoryDataSetReader;
import com.jiuqi.np.dataengine.fml.account.AccountQueryFieldInfo;
import com.jiuqi.np.dataengine.fml.account.AccountQueryModel;
import com.jiuqi.np.dataengine.fml.account.AccountUpdateDatas;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountQueryRegion
extends QueryRegion {
    private AccountMemoryDataSetReader dataReader = null;
    private AccountQueryModel mainQuery;
    private Map<QueryTable, Integer> dataQueryMap = new HashMap<QueryTable, Integer>();
    private List<AccountQueryModel> dataQueryList = new ArrayList<AccountQueryModel>();

    public AccountQueryRegion(DimensionSet dimensions) {
        super(dimensions, null);
    }

    @Override
    public void doInit(QueryContext context) throws ParseException {
        this.loopDimensions = new DimensionSet(this.dimensions);
        this.isFloat = true;
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        ArrayList<AccountQueryModel> leftJoinQuerys = new ArrayList<AccountQueryModel>();
        QueryFields mainQueryFields = null;
        for (QueryTable table : this.tableFields.keySet()) {
            DimensionValueSet masterKeys;
            IDataQuery dataQuery;
            if (table.isAccountTable() || table.isAccountRptTable()) {
                if (this.mainQuery == null) {
                    dataQuery = dataAccessProvider.newDataQuery();
                    dataQuery.setIgnoreDefaultOrderBy(true);
                    masterKeys = new DimensionValueSet(context.getMasterKeys());
                    dataQuery.setMasterKeys(masterKeys);
                    this.mainQuery = new AccountQueryModel(dataQuery, 0);
                    this.dataQueryMap.put(table, 0);
                    this.dataQueryList.add(this.mainQuery);
                    mainQueryFields = (QueryFields)this.tableFields.get(table);
                    continue;
                }
                mainQueryFields.addAll((QueryFields)this.tableFields.get(table));
                continue;
            }
            dataQuery = dataAccessProvider.newDataQuery();
            dataQuery.setIgnoreDefaultOrderBy(true);
            masterKeys = new DimensionValueSet(context.getMasterKeys());
            dataQuery.setMasterKeys(masterKeys);
            int queryIndex = leftJoinQuerys.size() + 1;
            leftJoinQuerys.add(new AccountQueryModel(dataQuery, queryIndex));
            this.dataQueryMap.put(table, queryIndex);
        }
        this.dataQueryList.addAll(leftJoinQuerys);
    }

    @Override
    public MemoryDataSetReader runQuery(QueryContext qContext, IQuerySqlUpdater sqlUpdater) throws Exception {
        this.createDataReader(qContext);
        if (this.isEmpty()) {
            return this.dataReader;
        }
        DataModelDefinitionsCache cache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        for (Map.Entry<QueryTable, Integer> entry : this.dataQueryMap.entrySet()) {
            QueryTable table = entry.getKey();
            int queryIndex = entry.getValue();
            QueryFields queryFields = (QueryFields)this.tableFields.get(table);
            AccountQueryModel accountQueryModel = this.dataQueryList.get(queryIndex);
            IDataQuery dataQuery = accountQueryModel.getDataQuery();
            for (QueryField queryField : queryFields) {
                try {
                    ColumnModelDefine columnModel = qContext.getExeContext().getCache().getDataModelDefinitionsCache().findField(queryField.getUID());
                    FieldDefine fieldDefine = qContext.getColumnModelFinder().findFieldDefine(columnModel);
                    AccountQueryFieldInfo info = this.dataReader.putIndex(cache, queryField, -1);
                    accountQueryModel.getFieldInfos().add(info);
                    info.setQueryIndex(queryIndex);
                    info.setColumnIndex(dataQuery.getColumnSize());
                    dataQuery.addColumn(fieldDefine);
                }
                catch (Exception e) {
                    qContext.getMonitor().exception(e);
                }
            }
        }
        qContext.setUpdateDatas(new AccountUpdateDatas());
        IDataTable dataTable = this.mainQuery.getDataQuery().executeQuery(qContext.getExeContext());
        this.dataReader.getDataTables().add(dataTable);
        if (this.dataQueryList.size() > 1) {
            for (int i = 1; i < this.dataQueryList.size(); ++i) {
                AccountQueryModel queryModel = this.dataQueryList.get(i);
                IDataQuery dataQuery = queryModel.getDataQuery();
                IDataTable leftJoinTable = dataQuery.executeQuery(qContext.getExeContext());
                this.dataReader.getDataTables().add(leftJoinTable);
            }
        }
        this.dataReader.loadMainTable(qContext, dataTable, this.mainQuery);
        for (int i = 1; i < this.dataReader.getDataTables().size(); ++i) {
            this.dataReader.loadLeftJoinTable(qContext, this.dataReader.getDataTables().get(i), this.dataQueryList.get(i));
        }
        return this.dataReader;
    }

    @Override
    public void createDataReader(QueryContext qContext) {
        this.dataReader = new AccountMemoryDataSetReader(qContext);
        qContext.setDataReader(this.dataReader);
        this.dataReader.setLoopDimensions(this.loopDimensions);
    }

    public AccountMemoryDataSetReader getDataReader() {
        return this.dataReader;
    }
}

