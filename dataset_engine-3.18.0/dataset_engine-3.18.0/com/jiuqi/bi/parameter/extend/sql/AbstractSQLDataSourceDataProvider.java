/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLQueryHelper
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 */
package com.jiuqi.bi.parameter.extend.sql;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSParamUtils;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.sql.SQLQueryExecutorFactoryManager;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterSelectMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.bi.parameter.storage.ParameterStorageException;
import com.jiuqi.bi.parameter.storage.ParameterStorageManager;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLQueryHelper;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract class AbstractSQLDataSourceDataProvider {
    SQLDataSourceModel dataSourceModel;
    String[] colNames;
    DataSourceMetaInfo metaInfo;

    AbstractSQLDataSourceDataProvider() {
    }

    boolean isTreeHierarchy() {
        ParameterHierarchyType type = this.dataSourceModel.getHireachyType();
        return type == ParameterHierarchyType.PARENT_SON || type == ParameterHierarchyType.STRUCTURECODE;
    }

    MemoryDataSet<ParameterColumnInfo> querySql(ParameterEngineEnv env, List<String> appointKeyValues, String filter, int topN) throws DataSourceException {
        String name = this.getKeyColName(env);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(name, appointKeyValues);
        return this.querySql(env, map, filter, topN);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    MemoryDataSet<ParameterColumnInfo> querySql(ParameterEngineEnv env, Map<String, Object> fieldFilters, String filter, int topN) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> dataset = this.buildEmptyDataset(this.isTreeHierarchy());
        String sql = this.dataSourceModel.getExpression();
        if (StringUtils.isEmpty((String)sql)) {
            throw new DataSourceException("\u6570\u636e\u96c6\u672a\u5b9a\u4e49SQL\u8bed\u53e5");
        }
        String connName = this.dataSourceModel.getDataSouce();
        try (Connection conn = DataSourceUtils.getConnection(connName);
             SQLQueryExecutor executor = this.buildQueryExecutor(conn, env);){
            this.doQuery(env, executor, fieldFilters, filter, dataset, topN);
        }
        catch (Exception e) {
            throw new DataSourceException("SQL\u6570\u636e\u6765\u6e90\u83b7\u5f97\u6570\u636e\u51fa\u9519\uff0c " + e.getMessage(), e);
        }
        if (this.isTreeHierarchy()) {
            this.checkDuplicateKey(dataset);
        }
        return dataset;
    }

    private void checkDuplicateKey(MemoryDataSet<ParameterColumnInfo> dataset) throws DataSourceException {
        Iterator itor = dataset.iterator();
        HashSet<String> set = new HashSet<String>();
        while (itor.hasNext()) {
            String key = ((DataRow)itor.next()).getString(0);
            if (set.add(key)) continue;
            throw new DataSourceException("\u53c2\u6570\u4e2d\u7684\u952e\u5217\u51fa\u73b0\u91cd\u590d\u8bb0\u5f55");
        }
    }

    private SQLQueryExecutor buildQueryExecutor(Connection conn, final ParameterEngineEnv env) throws SQLQueryException {
        SQLQueryExecutor sqlExecutor = SQLQueryExecutorFactoryManager.getInstance().createQueryExecutor(conn, env.getUserGuid(), "");
        sqlExecutor.registerListener(new ISQLQueryListener(){

            public ISQLQueryListener.ParamInfo findParam(String paramName) {
                return AbstractSQLDataSourceDataProvider.this.getParamInfo(env, paramName);
            }
        });
        sqlExecutor.open(this.dataSourceModel.getExpression());
        return sqlExecutor;
    }

    private boolean canFilterByDB(List<?> appointKeyValues) {
        if (appointKeyValues == null || appointKeyValues.isEmpty()) {
            return false;
        }
        return appointKeyValues.size() <= 100;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doQuery(ParameterEngineEnv env, SQLQueryExecutor executor, Map<String, Object> fieldFilters, String filter, MemoryDataSet<ParameterColumnInfo> dataset, int topN) throws SQLQueryException, SQLException, DataSourceException {
        boolean filterInMemory = false;
        HashSet keys = new HashSet();
        String keyColName = this.getKeyColName(env);
        Object v = fieldFilters.get(keyColName);
        if (!(v != null && v instanceof List && this.canFilterByDB((List)v) || v == null || !(v instanceof List) || ((List)v).isEmpty())) {
            filterInMemory = true;
            for (Object s : (List)v) {
                keys.add(s);
            }
        }
        try (ResultSet rs = topN > 0 ? executor.executeQuery(0, topN, fieldFilters, filter) : executor.executeQuery(fieldFilters, filter);){
            int columnCount = rs.getMetaData().getColumnCount();
            int dsColumnSize = dataset.getMetadata().getColumnCount();
            if (dsColumnSize < columnCount) {
                columnCount = dsColumnSize;
            }
            while (rs.next()) {
                String key = rs.getString(1);
                if (filterInMemory && !keys.contains(key)) continue;
                DataRow row = dataset.add();
                row.setString(0, key);
                if (columnCount > 1) {
                    row.setString(1, rs.getString(2));
                } else {
                    row.setString(1, rs.getString(1));
                }
                if (columnCount <= 2 || this.dataSourceModel.getHireachyType() != ParameterHierarchyType.PARENT_SON) continue;
                row.setString(2, rs.getString(3));
            }
        }
    }

    public void setDataSourceModel(SQLDataSourceModel dataSourceModel) {
        this.dataSourceModel = dataSourceModel;
    }

    public abstract MemoryDataSet<ParameterColumnInfo> filterChoiceValues(com.jiuqi.bi.parameter.model.ParameterModel var1, ParameterEngineEnv var2, boolean var3) throws DataSourceException;

    public abstract MemoryDataSet<ParameterColumnInfo> quickGetChoiceValues(com.jiuqi.bi.parameter.model.ParameterModel var1, ParameterEngineEnv var2, int var3, boolean var4) throws DataSourceException;

    public MemoryDataSet<ParameterColumnInfo> filterDefaultValues(com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        ParameterDefaultValueFilterMode defaultValueFilterMode = parameterModel.getDefaultValueFilterMode();
        if (defaultValueFilterMode == ParameterDefaultValueFilterMode.EXPRESSION) {
            throw new DataSourceException("SQL\u6570\u636e\u6765\u6e90\u7684\u53c2\u6570\u4e0d\u652f\u6301\u6309\u8868\u8fbe\u5f0f\u8fc7\u6ee4");
        }
        if (defaultValueFilterMode == ParameterDefaultValueFilterMode.NONE) {
            return this.buildEmptyDataset(this.isTreeHierarchy());
        }
        if (defaultValueFilterMode == ParameterDefaultValueFilterMode.APPOINT) {
            Object defaultValues = parameterModel.getDefaultValues();
            if (defaultValues == null) {
                return this.buildEmptyDataset(this.isTreeHierarchy());
            }
            List<String> appointKeyValues = this.buildAppointFilterKeyValues(defaultValues);
            if (appointKeyValues.isEmpty()) {
                return this.buildEmptyDataset(this.isTreeHierarchy());
            }
            String keyColName = this.getKeyColName(env);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(keyColName, appointKeyValues);
            MemoryDataSet<ParameterColumnInfo> dataset = this.querySql(env, map, null, -1);
            return DataSourceUtils.sortByAppointValues((List)defaultValues, dataset, this.getDataSourceMetaInfo(dataset));
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.FIRST)) {
            return this.queryFirstDefaultValue(parameterModel, env);
        }
        throw new DataSourceException("\u672a\u77e5\u7684\u53d6\u503c\u65b9\u5f0f\uff1a" + (Object)((Object)defaultValueFilterMode));
    }

    public MemoryDataSet<ParameterColumnInfo> filterValues(Object values, com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        List<String> appointKeyValues = this.buildAppointFilterKeyValues(values);
        if (appointKeyValues != null && appointKeyValues.isEmpty()) {
            return this.buildEmptyDataset(this.isTreeHierarchy());
        }
        String keyColName = this.getKeyColName(env);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(keyColName, appointKeyValues);
        return this.querySql(env, map, null, -1);
    }

    public MemoryDataSet<ParameterColumnInfo> quickSearch(List<String> values, ParameterEngineEnv env, int maxRecordSize, boolean showPath) throws DataSourceException {
        com.jiuqi.bi.parameter.model.ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
        List<String> choiceKeys = null;
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            choiceKeys = this.buildAppointFilterKeyValues(parameterModel.getDataSourceValues());
        } else if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            throw new DataSourceException("SQL\u53d6\u6570\u6765\u6e90\u6682\u4e0d\u652f\u6301\u6309\u8868\u8fbe\u5f0f\u8fc7\u6ee4");
        }
        String[] cols = this.getSQLColumnNames(env);
        String keycolName = cols[0];
        String namecolName = null;
        if (cols.length > 1) {
            namecolName = cols[1];
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < values.size(); ++i) {
            if (i > 0) {
                buf.append(" and ");
            }
            buf.append("(");
            List<String> conds = ParameterUtils.parseSearchValues(values.get(i));
            for (int j = 0; j < conds.size(); ++j) {
                String cond;
                if (j > 0) {
                    buf.append(" or ");
                }
                if (StringUtils.isNotEmpty((String)(cond = conds.get(j)))) {
                    cond = cond.toUpperCase();
                }
                buf.append("UPPER(").append(keycolName).append(")").append(" like '%").append(cond).append("%' ");
                if (!StringUtils.isNotEmpty((String)namecolName)) continue;
                buf.append(" or UPPER(").append(namecolName).append(")").append(" like '%").append(cond).append("%' ");
            }
            buf.append(")");
        }
        String filterExpr = buf.toString();
        HashMap<String, Object> fieldFilters = new HashMap<String, Object>();
        if (choiceKeys != null) {
            fieldFilters.put(keycolName, choiceKeys);
        }
        return this.querySql(env, fieldFilters, filterExpr, maxRecordSize);
    }

    public abstract MemoryDataSet<ParameterColumnInfo> getAllValues(String var1, ParameterEngineEnv var2) throws DataSourceException;

    public abstract MemoryDataSet<ParameterColumnInfo> queryFirstDefaultValue(com.jiuqi.bi.parameter.model.ParameterModel var1, ParameterEngineEnv var2) throws DataSourceException;

    public abstract MemoryDataSet<ParameterColumnInfo> getChildrenValue(ParameterEngineEnv var1, String var2, String var3, boolean var4) throws DataSourceException;

    MemoryDataSet<ParameterColumnInfo> buildEmptyDataset(boolean tree) {
        MemoryDataSet dataset = new MemoryDataSet();
        Metadata metadata = dataset.getMetadata();
        metadata.addColumn(new Column("code", 6));
        metadata.addColumn(new Column("name", 6));
        if (tree) {
            metadata.addColumn(new Column("parent", 6));
            metadata.addColumn(new Column("treeLeaf", 3));
        }
        return dataset;
    }

    List<String> buildAppointFilterKeyValues(Object dataSourceValues) {
        ArrayList<String> values;
        block5: {
            block4: {
                if (dataSourceValues == null) {
                    return null;
                }
                values = new ArrayList<String>();
                if (!(dataSourceValues instanceof List)) break block4;
                List dvm = (List)dataSourceValues;
                for (Object vm : dvm) {
                    values.add(((DataSourceValueModel)vm).getCode());
                }
                break block5;
            }
            if (!(dataSourceValues instanceof MemoryDataSet)) break block5;
            MemoryDataSet ds = (MemoryDataSet)dataSourceValues;
            int idx = ds.getMetadata().indexOf("code");
            if (idx == -1) {
                idx = 0;
            }
            for (DataRow row : ds) {
                values.add(row.getString(idx));
            }
        }
        return values;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    String[] getSQLColumnNames(ParameterEngineEnv env) throws DataSourceException {
        if (this.colNames == null) {
            String connName = this.dataSourceModel.getDataSouce();
            Connection conn = DataSourceUtils.getConnection(connName);
            try {
                try (SQLQueryExecutor executor = this.buildQueryExecutor(conn, env);
                     ResultSet rs = executor.executeQuery(0, 1);){
                    ResultSetMetaData metadata = rs.getMetaData();
                    this.colNames = new String[metadata.getColumnCount()];
                    for (int i = 0; i < this.colNames.length; ++i) {
                        this.colNames[i] = metadata.getColumnLabel(i + 1);
                    }
                }
                finally {
                    conn.close();
                }
            }
            catch (SQLQueryException e) {
                throw new DataSourceException(e);
            }
            catch (SQLException e) {
                throw new DataSourceException(e);
            }
        }
        return this.colNames;
    }

    String getKeyColName(ParameterEngineEnv env) throws DataSourceException {
        String[] names = this.getSQLColumnNames(env);
        if (names != null) {
            return names[0];
        }
        throw new DataSourceException("\u83b7\u53d6SQL\u5143\u6570\u636e\u5931\u8d25");
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ISQLQueryListener.ParamInfo getParamInfo(ParameterEngineEnv env, String name) {
        try {
            String range;
            String paramName;
            this.addParameterModel2Env(env, name);
            int index = name.indexOf(46);
            if (index != -1) {
                paramName = name.substring(0, index);
                range = name.substring(index + 1, name.length());
            } else {
                paramName = name;
                range = null;
            }
            com.jiuqi.bi.parameter.model.ParameterModel model = env.getParameterModel(paramName);
            if (model == null) {
                return null;
            }
            try {
                void var8_17;
                List<?> list = env.getValueAsList(paramName);
                if (model.isRangeParameter()) {
                    if (range == null) {
                        List<?> list2 = list;
                    } else if ("min".equalsIgnoreCase(range)) {
                        Object obj = list.get(0);
                    } else {
                        if (!"max".equalsIgnoreCase(range)) throw new SyntaxException("\u4e0d\u652f\u6301\u7684\u8bed\u6cd5:" + name);
                        Object obj = list.get(1);
                    }
                } else if (model.getSelectMode() == ParameterSelectMode.MUTIPLE) {
                    List<?> list3 = list;
                    ArrayData values = new ArrayData(DataType.translateToSyntaxType(model.getDataType()), list.size());
                    for (int i = 0; i < list.size(); ++i) {
                        values.set(i, list.get(i));
                    }
                    ArrayData arrayData = values;
                } else if (list == null || list.size() == 0) {
                    Object var8_15 = null;
                } else {
                    Object obj = list.get(0);
                }
                if (!model.getSelectMode().equals((Object)ParameterSelectMode.MUTIPLE)) return new ISQLQueryListener.ParamInfo(paramName, DataType.translateToSyntaxType(model.getDataType()), (Object)var8_17);
                return new ISQLQueryListener.ParamInfo(paramName, 11, (Object)var8_17);
            }
            catch (ParameterException e) {
                throw new SyntaxException("\u83b7\u53d6\u53c2\u6570\u4fe1\u606f\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addParameterModel2Env(ParameterEngineEnv env, String name) throws ParameterStorageException, ParameterException {
        if (!env.containsParameter(name)) {
            com.jiuqi.bi.parameter.model.ParameterModel parameterModel = ParameterStorageManager.getInstance().findModel(name, env.getOwner_Name(), env.getOwner_Type(), null);
            if (parameterModel == null) {
                parameterModel = ParameterStorageManager.getInstance().findModel(name, null, null, null);
            }
            ArrayList<com.jiuqi.bi.parameter.model.ParameterModel> parameterModels = new ArrayList<com.jiuqi.bi.parameter.model.ParameterModel>();
            if (parameterModel != null) {
                parameterModels.add(parameterModel);
                env.addParameterModels(parameterModels);
            }
        }
    }

    public DataSourceMetaInfo getDataSourceMetaInfo(MemoryDataSet<ParameterColumnInfo> dataset) throws DataSourceException {
        try {
            if (this.metaInfo != null) {
                return this.metaInfo;
            }
            this.metaInfo = new DataSourceMetaInfo();
            Metadata metadata = dataset.getMetadata();
            List columns = metadata.getColumns();
            if (columns.size() > 0) {
                DataSourceAttrBean bean = new DataSourceAttrBean();
                bean.setCurrAttrName(((Column)columns.get(0)).getName());
                bean.setKeyColName(((Column)columns.get(0)).getName());
                bean.setTitle(((Column)columns.get(0)).getTitle());
                if (columns.size() > 1) {
                    bean.setNameColName(((Column)columns.get(1)).getName());
                }
                this.metaInfo.getAttrBeans().add(bean);
            }
            return this.metaInfo;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(DataSourceModel dataSourceModel) throws DataSourceException {
        com.jiuqi.bi.parameter.model.ParameterModel model = new com.jiuqi.bi.parameter.model.ParameterModel();
        model.setDataSourceModel(dataSourceModel);
        ParameterModel pm = null;
        try {
            pm = DSParamUtils.convertParameterModel(model);
        }
        catch (com.jiuqi.nvwa.framework.parameter.ParameterException e) {
            throw new DataSourceException(e);
        }
        pm.setDatasource(pm.getDatasource());
        pm.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
        SQLQueryHelper queryHelper = new SQLQueryHelper(pm);
        try {
            return queryHelper.getSQLFields();
        }
        catch (com.jiuqi.nvwa.framework.parameter.ParameterException e) {
            throw new DataSourceException(e);
        }
    }
}

