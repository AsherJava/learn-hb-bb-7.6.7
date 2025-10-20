/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeNode
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
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.sql.SQLQueryExecutorFactoryManager;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterDataRowIterator;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterObjectVistor;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceHierarchyType;
import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProviderEx;
import com.jiuqi.bi.parameter.manager.IDataSourceDataQuickProvider;
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
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeNode;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLQueryHelper;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqlDataSourceDataProvider
implements IDataSourceDataProviderEx,
IDataSourceDataQuickProvider {
    private SQLDataSourceModel dataSourceModel;
    private List<Integer> structureCodes = new ArrayList<Integer>();
    private TreeNode _rootNode;
    private String paramKey = "";

    public SqlDataSourceDataProvider(DataSourceModel dataSource) {
        this.dataSourceModel = (SQLDataSourceModel)dataSource;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            return this.getFirstLevelValues(env);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            Object dataSourceValues = parameterModel.getDataSourceValues();
            if (dataSourceValues == null) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            if (dataSourceValues instanceof List) {
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.dataSourceValueModels2ParamterDataSet((List)dataSourceValues);
                this.fillParentField(dataSet, env);
                MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(dataSet, this.getAllValues(false, env), this.getDataSourceMetaInfo());
                resultDataSet = this.buildTreeDataSet(resultDataSet, env);
                return DataSourceUtils.sortByAppointValues(dataSet, resultDataSet, this.getDataSourceMetaInfo());
            }
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)dataSourceValues), this.getAllValues(false, env), this.getDataSourceMetaInfo());
            resultDataSet = this.buildTreeDataSet(resultDataSet, env);
            return DataSourceUtils.sortByAppointValues((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)dataSourceValues), resultDataSet, this.getDataSourceMetaInfo());
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            String dataSourceFilter = parameterModel.getDataSourceFilter();
            return this.filterValuesForExpr(dataSourceFilter, env);
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> filterOnlyChoiceValues(com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            return this.getAllValues(false, env);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            Object dataSourceValues = parameterModel.getDataSourceValues();
            if (dataSourceValues == null) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            if (dataSourceValues instanceof List) {
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.dataSourceValueModels2ParamterDataSet((List)dataSourceValues, this.getDataSourceMetaInfo());
                MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(dataSet, this.getAllValues(false, env), this.getDataSourceMetaInfo());
                this.fillParentField(resultDataSet, env);
                return resultDataSet;
            }
            return DataSourceUtils.removeDataRowsNotInTargetDataSet((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)dataSourceValues), this.getAllValues(false, env), this.getDataSourceMetaInfo());
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            String dataSourceFilter = parameterModel.getDataSourceFilter();
            return this.filterValuesForExpr(dataSourceFilter, env);
        }
        return null;
    }

    private void fillParentField(MemoryDataSet<ParameterColumnInfo> dataSet, ParameterEngineEnv env) throws DataSourceException {
        try {
            if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON)) {
                MemoryDataSet<ParameterColumnInfo> allDataSet = this.getAllValues(false, env);
                dataSet.getMetadata().addColumn(new Column("parent", 6));
                int keyIndex = dataSet.getMetadata().indexOf("code");
                int nameIndex = dataSet.getMetadata().indexOf("name");
                int parentIndex = dataSet.getMetadata().indexOf("parent");
                for (DataRow dataRow : dataSet) {
                    for (DataRow allDataRow : allDataSet) {
                        if (!dataRow.getString(keyIndex).equals(allDataRow.getString(keyIndex)) || !dataRow.getString(nameIndex).equals(allDataRow.getString(nameIndex))) continue;
                        dataRow.setString(parentIndex, allDataRow.getString(2));
                        dataRow.commit();
                    }
                }
            }
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> filterValuesForExpr(String expression, ParameterEngineEnv env) throws DataSourceException {
        throw new DataSourceException("SQL\u6570\u636e\u6765\u6e90\u7684\u53c2\u6570\u4e0d\u652f\u6301\u6b64\u65b9\u6cd5\u7684\u8c03\u7528\uff01");
    }

    private MemoryDataSet<ParameterColumnInfo> buildTreeDataSet(MemoryDataSet<ParameterColumnInfo> values, ParameterEngineEnv env) throws DataSourceException {
        if (this.dataSourceModel.getSQLHierarchyType().equals((Object)SQLDataSourceHierarchyType.PARENTMODE)) {
            return this.buildParent_SonDataSet(values, env);
        }
        if (this.dataSourceModel.getSQLHierarchyType().equals((Object)SQLDataSourceHierarchyType.STRUCTURECODE)) {
            this.parseStructureCodes();
            return DataSourceUtils.buildStructureCodeDataSet(values, this.dataSourceModel.getStructureCode(), this.isShortCode(values, this.getSumCodeLength()));
        }
        return values;
    }

    private boolean isShortCode(MemoryDataSet<ParameterColumnInfo> allValues, int length) {
        for (DataRow dataRow : allValues) {
            String code = dataRow.getString(0);
            if (code.length() == length) continue;
            return true;
        }
        return false;
    }

    private MemoryDataSet<ParameterColumnInfo> getAllValues(boolean isFirstLevel, ParameterEngineEnv env) throws DataSourceException {
        if (isFirstLevel) {
            return this.getFirstLevelValues(env);
        }
        return this.getSQLAllValues(env);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterDefaultValues(com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        ParameterDefaultValueFilterMode defaultValueFilterMode = parameterModel.getDefaultValueFilterMode();
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.APPOINT)) {
            Object defaultValues = parameterModel.getDefaultValues();
            if (defaultValues == null) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            if (defaultValues instanceof List) {
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.dataSourceValueModels2ParamterDataSet((List)defaultValues);
                dataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(dataSet, this.getAllValues(false, env), this.getDataSourceMetaInfo());
                return dataSet;
            }
            MemoryDataSet<ParameterColumnInfo> dataSet = (MemoryDataSet<ParameterColumnInfo>)defaultValues;
            dataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(dataSet, this.getAllValues(false, env), this.getDataSourceMetaInfo());
            return dataSet;
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.FIRST)) {
            MemoryDataSet<ParameterColumnInfo> dataSet = this.filterChoiceValues(parameterModel, env);
            dataSet = this.buildParent_SonDataSet(dataSet, env);
            return DataSourceUtils.getFirstValue(ParameterUtils.reverseDataSet(parameterModel, dataSet));
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.EXPRESSION)) {
            String expression = parameterModel.getDefalutValueFilter();
            return this.filterValuesForExpr(expression, env);
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.NONE)) {
            return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
        }
        return null;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> searchValues(List<String> values, boolean isSearchFromChoiceValues, ParameterEngineEnv env) throws DataSourceException {
        try {
            MemoryDataSet dataSet = new MemoryDataSet();
            com.jiuqi.bi.parameter.model.ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            MemoryDataSet<ParameterColumnInfo> choiceValues = isSearchFromChoiceValues ? this.filterAllChoiceValues(parameterModel, env) : this.getAllValues(false, env);
            if (values.size() == 0) {
                return choiceValues;
            }
            dataSet.getMetadata().addColumn(new Column("code", 6));
            dataSet.getMetadata().addColumn(new Column("name", 6));
            dataSet.getMetadata().addColumn(new Column("parent", 6));
            for (DataRow dataRow : choiceValues) {
                String code = dataRow.getString(0);
                String name = dataRow.getString(1);
                if (!DataSourceUtils.isContainsSearchValues(code, values) && !DataSourceUtils.isContainsSearchValues(name, values)) continue;
                DataRow resultDataRow = dataSet.add();
                this.copyDataRow(dataRow, resultDataRow, (Metadata<ParameterColumnInfo>)choiceValues.getMetadata());
                resultDataRow.commit();
            }
            MemoryDataSet<ParameterColumnInfo> resultDataSet = this.buildSearchMemoryDataSet((MemoryDataSet<ParameterColumnInfo>)dataSet);
            if (isSearchFromChoiceValues) {
                return DataSourceUtils.sortByAppointValues(choiceValues, resultDataSet, this.getDataSourceMetaInfo());
            }
            return dataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> buildSearchMemoryDataSet(MemoryDataSet<ParameterColumnInfo> dataSet) throws DataSourceException {
        try {
            if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON)) {
                dataSet.getMetadata().addColumn(new Column("treeLeaf", 6));
                int index = dataSet.getMetadata().indexOf("treeLeaf");
                for (DataRow dataRow : dataSet) {
                    dataRow.setInt(index, 1);
                    dataRow.commit();
                }
            } else {
                return dataSet;
            }
            return dataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> buildParent_SonDataSet(MemoryDataSet<ParameterColumnInfo> allValues, ParameterEngineEnv env) throws DataSourceException {
        if (this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.PARENT_SON)) {
            try {
                return DataSourceUtils.buildParameter_SonDataSet(allValues, "parent", this.getDataSourceMetaInfo().getAttrBeans().get(0));
            }
            catch (Exception e) {
                throw new DataSourceException("\u6784\u5efa\u7236\u5b50\u5c42\u7ea7\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
        }
        return allValues;
    }

    private void copyDataRow(DataRow orgDataRow, DataRow targetDataRow, Metadata<ParameterColumnInfo> metadata) {
        for (int i = 0; i < metadata.size(); ++i) {
            targetDataRow.setValue(i, orgDataRow.getValue(i));
        }
    }

    @Override
    public void init(ParameterEngineEnv env) throws DataSourceException {
    }

    private TreeNode getRootNode(ParameterEngineEnv env) throws DataSourceException {
        String k;
        try {
            Map<com.jiuqi.bi.parameter.model.ParameterModel, Object> map = ParameterUtils.getCascadeParameterValueModel(this.dataSourceModel, env);
            StringBuilder buf = new StringBuilder();
            for (com.jiuqi.bi.parameter.model.ParameterModel pm : map.keySet()) {
                buf.append(pm.getName()).append(":").append(map.get(pm));
                buf.append(";");
            }
            k = buf.toString();
        }
        catch (ParameterException e1) {
            throw new DataSourceException(e1);
        }
        if (this._rootNode == null || !k.equals(this.paramKey)) {
            this.paramKey = k;
            SQLDataSourceHierarchyType hierType = this.dataSourceModel.getSQLHierarchyType();
            if (hierType == SQLDataSourceHierarchyType.NONE) {
                return null;
            }
            try {
                TreeBuilder treeBuilder;
                MemoryDataSet<ParameterColumnInfo> allValues;
                if (env.getQueryProperty("initAllValues") != null) {
                    allValues = this.getSQLAllValues(env);
                } else {
                    com.jiuqi.bi.parameter.model.ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
                    allValues = this.filterOnlyChoiceValues(parameterModel, env);
                }
                allValues = this.removeDuplicateDatas(allValues);
                if (hierType == SQLDataSourceHierarchyType.PARENTMODE) {
                    treeBuilder = TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new ParameterObjectVistor());
                    treeBuilder.setSortMode(1);
                    this._rootNode = treeBuilder.build((Iterator)new ParameterDataRowIterator(allValues));
                } else if (hierType == SQLDataSourceHierarchyType.STRUCTURECODE) {
                    this.parseStructureCodes();
                    treeBuilder = TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)new ParameterObjectVistor(), (String)this.dataSourceModel.getStructureCode(), (boolean)this.isShortCode(allValues, this.getSumCodeLength()));
                    treeBuilder.setSortMode(1);
                    this._rootNode = treeBuilder.build((Iterator)new ParameterDataRowIterator(allValues));
                }
            }
            catch (Exception e) {
                throw new DataSourceException("\u521d\u59cb\u5316\u53c2\u6570\u7f13\u5b58\u4fe1\u606f\u51fa\u9519\uff0c" + e.getMessage(), e.getCause());
            }
        }
        return this._rootNode;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        if (this.dataSourceModel.getSQLHierarchyType().equals((Object)SQLDataSourceHierarchyType.NONE)) {
            return this.getSQLAllValues(env);
        }
        return this.getFirstLevelValues(env);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MemoryDataSet<ParameterColumnInfo> getSQLAllValues(final ParameterEngineEnv env) throws DataSourceException {
        MemoryDataSet dataSet = new MemoryDataSet();
        dataSet.getMetadata().addColumn(new Column("code", 6));
        dataSet.getMetadata().addColumn(new Column("name", 6));
        dataSet.getMetadata().addColumn(new Column("parent", 6));
        String sql = this.dataSourceModel.getExpression();
        if (StringUtils.isEmpty((String)sql)) {
            return dataSet;
        }
        try {
            String connName = this.dataSourceModel.getDataSouce();
            try (Connection connection = this.getConnection(connName);){
                SQLQueryExecutor sqlExecutor = SQLQueryExecutorFactoryManager.getInstance().createQueryExecutor(connection, env.getUserGuid(), connName);
                sqlExecutor.registerListener(new ISQLQueryListener(){

                    public ISQLQueryListener.ParamInfo findParam(String paramName) {
                        return SqlDataSourceDataProvider.this.getParamInfo(env, paramName);
                    }
                });
                sqlExecutor.open(sql);
                try {
                    ResultSet rs = sqlExecutor.executeQuery();
                    int columnCount = rs.getMetaData().getColumnCount();
                    try {
                        while (rs.next()) {
                            DataRow row;
                            block17: {
                                row = dataSet.add();
                                row.setString(0, rs.getString(1));
                                if (columnCount == 1) {
                                    row.setString(1, rs.getString(1));
                                } else {
                                    row.setString(1, rs.getString(2));
                                }
                                try {
                                    String parent = rs.getString(3);
                                    if (parent == null) break block17;
                                    row.setString(2, parent);
                                }
                                catch (Exception e) {
                                    continue;
                                }
                            }
                            row.commit();
                        }
                    }
                    finally {
                        rs.close();
                    }
                }
                finally {
                    sqlExecutor.close();
                }
            }
            return this.removeDuplicateDatas((MemoryDataSet<ParameterColumnInfo>)dataSet);
        }
        catch (Exception e) {
            throw new DataSourceException("SQL\u6570\u636e\u6765\u6e90\u83b7\u5f97\u6570\u636e\u51fa\u9519\uff0c " + e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> removeDuplicateDatas(MemoryDataSet<ParameterColumnInfo> allValues) throws DataSetException {
        if (allValues == null || allValues.size() == 0 || allValues.getMetadata().size() == 0) {
            return allValues;
        }
        HashMap<String, Integer> keyValueAndIndex = new HashMap<String, Integer>();
        ArrayList removedIndexs = new ArrayList();
        for (int i = 0; i < allValues.size(); ++i) {
            DataRow row = allValues.get(i);
            String keyValue = row.getString(0);
            if (keyValueAndIndex.containsKey(keyValue)) {
                removedIndexs.add(keyValueAndIndex.get(keyValue));
            }
            keyValueAndIndex.put(keyValue, i);
        }
        MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet((Metadata<ParameterColumnInfo>)allValues.getMetadata());
        for (int i = 0; i < allValues.size(); ++i) {
            if (removedIndexs.contains(i)) continue;
            DataSourceUtils.addDataRow2DataSet(allValues.get(i), resultDataSet);
        }
        return resultDataSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String[] getSQLColumnNames(final ParameterEngineEnv env) throws DataSourceException {
        String[] stringArray;
        String connName = this.dataSourceModel.getDataSouce();
        Connection connection = this.getConnection(connName);
        try {
            SQLQueryExecutor sqlExecutor = SQLQueryExecutorFactoryManager.getInstance().createQueryExecutor(connection, env.getUserGuid(), connName);
            sqlExecutor.registerListener(new ISQLQueryListener(){

                public ISQLQueryListener.ParamInfo findParam(String paramName) {
                    return SqlDataSourceDataProvider.this.getParamInfo(env, paramName);
                }
            });
            sqlExecutor.open(this.dataSourceModel.getExpression());
            ResultSet rs = sqlExecutor.executeQuery(0, 1);
            ResultSetMetaData metadata = rs.getMetaData();
            String[] cols = new String[metadata.getColumnCount()];
            for (int i = 0; i < cols.length; ++i) {
                cols[i] = metadata.getColumnLabel(i + 1);
            }
            stringArray = cols;
        }
        catch (Throwable throwable) {
            try {
                connection.close();
                throw throwable;
            }
            catch (Exception e) {
                throw new DataSourceException(e);
            }
        }
        connection.close();
        return stringArray;
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

    private MemoryDataSet<ParameterColumnInfo> getFirstLevelValues(ParameterEngineEnv env) throws DataSourceException {
        try {
            if (this.dataSourceModel.getSQLHierarchyType().equals((Object)SQLDataSourceHierarchyType.NONE)) {
                return this.getAllValues(false, env);
            }
            return DataSourceUtils.getFirstLevelData(this.getRootNode(env), this.getDataSourceMetaInfo());
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> getChildrenValue(String parentValue, int level, boolean isAllSubLevel, String filterExpr, ParameterEngineEnv env, boolean isFromRcp) throws DataSourceException {
        try {
            this.parseStructureCodes();
            com.jiuqi.bi.parameter.model.ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            Map<String, String> parentValueMap = DataSourceUtils.parseParentValue(parentValue, this.dataSourceModel.getHireachyType());
            parentValue = parentValueMap.get("code".toUpperCase());
            if (isAllSubLevel) {
                MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getAllSubLevelData(this.getRootNode(env), parentValue, level, this.getDataSourceMetaInfo());
                return DataSourceUtils.removeDataRowsNotInTargetDataSet(resultDataSet, this.filterOnlyChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
            }
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getDirectSubLevelData(this.getRootNode(env), parentValue, level, this.getDataSourceMetaInfo());
            if (isFromRcp) {
                return resultDataSet;
            }
            return DataSourceUtils.removeDataRowsNotInTargetDataSet(resultDataSet, this.filterOnlyChoiceValues(parameterModel, env), this.getDataSourceMetaInfo());
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private int getSumCodeLength() {
        int codeLengthSum = 0;
        for (Integer codeLength : this.structureCodes) {
            codeLengthSum += codeLength.intValue();
        }
        return codeLengthSum;
    }

    @Override
    public DataSourceMetaInfo getDataSourceMetaInfo() throws DataSourceException {
        DataSourceMetaInfo metaInfo = new DataSourceMetaInfo();
        DataSourceAttrBean attrBean = new DataSourceAttrBean();
        if (this.dataSourceModel.getSQLHierarchyType().equals((Object)SQLDataSourceHierarchyType.NONE)) {
            metaInfo.setLevelDepth(0);
            attrBean.setTitle("\u540d\u79f0;\u7f16\u7801");
        } else {
            metaInfo.setParentSonMode(true);
            attrBean.setTitle("\u540d\u79f0");
            metaInfo.setLevelDepth(3);
        }
        attrBean.setCurrAttrName("code");
        attrBean.setKeyColName("code");
        attrBean.setNameColName("name");
        metaInfo.getAttrBeans().add(attrBean);
        return metaInfo;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String parentValue, int level, boolean isAllSubLevel, String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        return this.getChildrenValue(parentValue, level, isAllSubLevel, filterExpr, env, false);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String parentValue, int level, String filterExpr, ParameterEngineEnv env, boolean isAllSubLevel) throws DataSourceException {
        return this.getChildrenValue(parentValue, level, isAllSubLevel, filterExpr, env, true);
    }

    private void parseStructureCodes() {
        this.structureCodes.clear();
        String structureCode = this.dataSourceModel.getStructureCode();
        if (StringUtils.isNotEmpty((String)structureCode)) {
            String[] codes;
            for (String code : codes = structureCode.split(",")) {
                this.structureCodes.add(Integer.valueOf(code));
            }
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterValues(Object values, com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        MemoryDataSet dataSet = (MemoryDataSet)values;
        MemoryDataSet<ParameterColumnInfo> allValues = this.getAllValues(false, env);
        for (DataRow dataRow : dataSet) {
            String key = dataRow.getString(0);
            dataRow.setValue(1, this.getTitleFromAllValue(key, allValues));
        }
        return DataSourceUtils.removeDataRowsNotInTargetDataSet((MemoryDataSet<ParameterColumnInfo>)dataSet, allValues, this.getDataSourceMetaInfo());
    }

    private Object getTitleFromAllValue(String key, MemoryDataSet<ParameterColumnInfo> allValues) {
        for (DataRow dataRow : allValues) {
            if (!dataRow.getString(0).equals(key)) continue;
            return dataRow.getValue(1);
        }
        return null;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterAllChoiceValues(com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            return this.getSQLAllValues(env);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            Object dataSourceValues = parameterModel.getDataSourceValues();
            if (dataSourceValues == null) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            if (dataSourceValues instanceof List) {
                MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.dataSourceValueModels2ParamterDataSet((List)dataSourceValues);
                MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(dataSet, this.getAllValues(false, env), this.getDataSourceMetaInfo());
                return DataSourceUtils.sortByAppointValues(dataSet, resultDataSet, this.getDataSourceMetaInfo());
            }
            MemoryDataSet<ParameterColumnInfo> resultDataSet = (MemoryDataSet<ParameterColumnInfo>)dataSourceValues;
            resultDataSet = DataSourceUtils.removeDataRowsNotInTargetDataSet(resultDataSet, this.getAllValues(false, env), this.getDataSourceMetaInfo());
            return DataSourceUtils.sortByAppointValues((MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)dataSourceValues), resultDataSet, this.getDataSourceMetaInfo());
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            String dataSourceFilter = parameterModel.getDataSourceFilter();
            return this.filterValuesForExpr(dataSourceFilter, env);
        }
        return null;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> fuzzySearch(List<String> values, ParameterEngineEnv env) throws DataSourceException {
        return this.searchValues(values, true, env);
    }

    @Override
    public String formatValue(String value, DataSourceAttrBean attrBean) throws DataSourceException {
        return value;
    }

    @Override
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

    private MemoryDataSet<ParameterColumnInfo> createEmptyDataset() {
        MemoryDataSet dataSet = new MemoryDataSet();
        dataSet.getMetadata().addColumn(new Column("code", 6));
        dataSet.getMetadata().addColumn(new Column("name", 6));
        dataSet.getMetadata().addColumn(new Column("parent", 6));
        return dataSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MemoryDataSet<ParameterColumnInfo> getTopNValues(final ParameterEngineEnv env, int n, Map<String, Object> fieldFilters, String filterExpr) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> dataSet = this.createEmptyDataset();
        String sql = this.dataSourceModel.getExpression();
        if (StringUtils.isEmpty((String)sql)) {
            return dataSet;
        }
        String connName = this.dataSourceModel.getDataSouce();
        try (Connection connection = this.getConnection(connName);){
            SQLQueryExecutor sqlExecutor = SQLQueryExecutorFactoryManager.getInstance().createQueryExecutor(connection, env.getUserGuid(), connName);
            sqlExecutor.registerListener(new ISQLQueryListener(){

                public ISQLQueryListener.ParamInfo findParam(String paramName) {
                    return SqlDataSourceDataProvider.this.getParamInfo(env, paramName);
                }
            });
            sqlExecutor.open(sql);
            try {
                ResultSet rs = sqlExecutor.executeQuery(0, n, fieldFilters, filterExpr);
                int columnCount = rs.getMetaData().getColumnCount();
                int keyColIdx = 1;
                int nameColIdx = columnCount == 1 ? 1 : 2;
                int parentColIdx = columnCount >= 3 ? 3 : -1;
                try {
                    while (rs.next()) {
                        DataRow row = dataSet.add();
                        row.setString(0, rs.getString(keyColIdx));
                        row.setString(1, rs.getString(nameColIdx));
                        row.setString(2, parentColIdx > 0 ? rs.getString(parentColIdx) : null);
                        row.commit();
                    }
                }
                finally {
                    rs.close();
                }
            }
            finally {
                sqlExecutor.close();
            }
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
        return dataSet;
    }

    /*
     * Exception decompiling
     */
    private String getKeyColName(ParameterEngineEnv env) throws DataSourceException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private List<String> getChoiceKeyFromAppointDatasource(com.jiuqi.bi.parameter.model.ParameterModel parameterModel) throws DataSourceException {
        ArrayList<String> choiceKeys = new ArrayList<String>();
        Object dataSourceValues = parameterModel.getDataSourceValues();
        if (dataSourceValues instanceof List) {
            List dsvs = (List)dataSourceValues;
            for (Object obj : dsvs) {
                DataSourceValueModel dsv = (DataSourceValueModel)obj;
                choiceKeys.add(dsv.getCode());
            }
        } else if (dataSourceValues instanceof MemoryDataSet) {
            MemoryDataSet dsvs = (MemoryDataSet)dataSourceValues;
            int idx = dsvs.getMetadata().indexOf("code");
            if (idx >= 0) {
                Iterator itor = dsvs.iterator();
                while (itor.hasNext()) {
                    choiceKeys.add(((DataRow)itor.next()).getString(idx));
                }
            } else {
                throw new DataSourceException("\u6765\u6e90\u7ed3\u679c\u96c6\u4e2d\u4e0d\u5305\u542b\u952e\u5217\u5b57\u6bb5");
            }
        }
        return choiceKeys;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickSearch(List<String> values, ParameterEngineEnv env, int maxRecordSize, boolean showPath) throws DataSourceException {
        com.jiuqi.bi.parameter.model.ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
        List<String> choiceKeys = null;
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            choiceKeys = this.getChoiceKeyFromAppointDatasource(parameterModel);
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
        return this.getTopNValues(env, maxRecordSize, fieldFilters, filterExpr);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> fillDatasetByKey(MemoryDataSet<ParameterColumnInfo> dataSet, com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        if (dataSet.isEmpty()) {
            return dataSet;
        }
        ArrayList<Object> keys = new ArrayList<Object>();
        Iterator itor = dataSet.iterator();
        while (itor.hasNext()) {
            keys.add(((DataRow)itor.next()).getValue(0));
        }
        HashMap<String, Object> fieldFilters = new HashMap<String, Object>();
        String keyColName = this.getKeyColName(env);
        fieldFilters.put(keyColName, keys);
        return this.getTopNValues(env, 500, fieldFilters, null);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickGetChoiceValues(com.jiuqi.bi.parameter.model.ParameterModel parameterModel, ParameterEngineEnv env, int maxRecordSize, boolean isFirstLevel) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            SQLDataSourceHierarchyType hierType = this.dataSourceModel.getSQLHierarchyType();
            if (hierType.equals((Object)SQLDataSourceHierarchyType.NONE) || !isFirstLevel) {
                return this.getTopNValues(env, 500, null, null);
            }
            return DataSourceUtils.getFirstLevelData(this.getRootNode(env), this.getDataSourceMetaInfo());
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            List<String> choiceKeys = this.getChoiceKeyFromAppointDatasource(parameterModel);
            if (choiceKeys == null || choiceKeys.isEmpty()) {
                return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            }
            HashMap<String, Object> fieldFilters = new HashMap<String, Object>();
            if (choiceKeys != null) {
                String[] cols = this.getSQLColumnNames(env);
                fieldFilters.put(cols[0], choiceKeys);
            }
            MemoryDataSet<ParameterColumnInfo> dataSet = this.getTopNValues(env, 500, fieldFilters, null);
            dataSet = this.buildTreeDataSet(dataSet, env);
            return dataSet;
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            throw new DataSourceException("SQL\u53d6\u6570\u6765\u6e90\u6682\u4e0d\u652f\u6301\u6309\u8868\u8fbe\u5f0f\u8fc7\u6ee4");
        }
        throw new DataSourceException("\u672a\u77e5\u7684\u6570\u636e\u6765\u6e90\u8fc7\u6ee4\u65b9\u5f0f\uff1a" + (Object)((Object)dataSourceFilterMode));
    }

    protected Connection getConnection(String connName) throws Exception {
        return DataSourceUtils.getConnection(connName);
    }
}

