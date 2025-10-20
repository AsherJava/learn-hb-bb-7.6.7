/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 */
package com.jiuqi.bi.parameter.extend.sql;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.sql.AbstractSQLDataSourceDataProvider;
import com.jiuqi.bi.parameter.extend.sql.HireachyNoneDataProvider;
import com.jiuqi.bi.parameter.extend.sql.HireachyParentSonDataProvider;
import com.jiuqi.bi.parameter.extend.sql.HireachyStructedDataProvider;
import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceHierarchyType;
import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProviderEx;
import com.jiuqi.bi.parameter.manager.IDataSourceDataQuickProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SQLDSDataProvider
implements IDataSourceDataProviderEx,
IDataSourceDataQuickProvider {
    private AbstractSQLDataSourceDataProvider proxy;
    private SQLDataSourceModel dataSourceModel;

    public SQLDSDataProvider(DataSourceModel model) {
        this.dataSourceModel = (SQLDataSourceModel)model;
        SQLDataSourceHierarchyType type = this.dataSourceModel.getSQLHierarchyType();
        this.proxy = type == SQLDataSourceHierarchyType.PARENTMODE ? new HireachyParentSonDataProvider() : (type == SQLDataSourceHierarchyType.STRUCTURECODE ? new HireachyStructedDataProvider() : new HireachyNoneDataProvider());
        this.proxy.setDataSourceModel(this.dataSourceModel);
    }

    @Override
    public void init(ParameterEngineEnv env) throws DataSourceException {
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterDefaultValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        return this.proxy.filterDefaultValues(parameterModel, env);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode == DataSourceFilterMode.EXPRESSION) {
            throw new DataSourceException("SQL\u6570\u636e\u6765\u6e90\u7684\u53c2\u6570\u4e0d\u652f\u6301\u6309\u8868\u8fbe\u5f0f\u8fc7\u6ee4");
        }
        return this.proxy.filterChoiceValues(parameterModel, env, true);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterValues(Object values, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        return this.proxy.filterValues(values, parameterModel, env);
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
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        return this.proxy.getAllValues(filterExpr, env);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> searchValues(List<String> values, boolean isSearchFromChoiceValues, ParameterEngineEnv env) throws DataSourceException {
        throw new DataSourceException("\u4f7f\u7528quickSearch\u63a5\u53e3\u66ff\u4ee3");
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> fuzzySearch(List<String> values, ParameterEngineEnv env) throws DataSourceException {
        throw new DataSourceException("\u4f7f\u7528quickSearch\u63a5\u53e3\u66ff\u4ee3");
    }

    @Override
    public String formatValue(String value, DataSourceAttrBean attrBean) throws DataSourceException {
        return value;
    }

    @Override
    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(DataSourceModel dataSourceModel) throws DataSourceException {
        return this.proxy.getDataSourceCandidateFields(dataSourceModel);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickGetChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env, int maxRecordSize, boolean isFirstLevel) throws DataSourceException {
        return this.proxy.quickGetChoiceValues(parameterModel, env, maxRecordSize, isFirstLevel);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> fillDatasetByKey(MemoryDataSet<ParameterColumnInfo> dataSet, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        if (dataSet.isEmpty()) {
            return dataSet;
        }
        ArrayList<String> appointKeyValues = new ArrayList<String>();
        Iterator itor = dataSet.iterator();
        while (itor.hasNext()) {
            appointKeyValues.add(((DataRow)itor.next()).getString(0));
        }
        return this.proxy.querySql(env, appointKeyValues, null, -1);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickSearch(List<String> values, ParameterEngineEnv env, int maxRecordSize, boolean showPath) throws DataSourceException {
        return this.proxy.quickSearch(values, env, maxRecordSize, showPath);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String parentValue, int level, boolean isAllSubLevel, String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        Map<String, String> parentValueMap = DataSourceUtils.parseParentValue(parentValue, this.dataSourceModel.getHireachyType());
        String parent = parentValueMap.get("code".toUpperCase());
        return this.proxy.getChildrenValue(env, parent, filterExpr, isAllSubLevel);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterAllChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        return this.proxy.filterChoiceValues(parameterModel, env, false);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChildrenValue(String parentValue, int level, String filterExpr, ParameterEngineEnv env, boolean isAllSubLevel) throws DataSourceException {
        Map<String, String> parentValueMap = DataSourceUtils.parseParentValue(parentValue, this.dataSourceModel.getHireachyType());
        String parent = parentValueMap.get("code".toUpperCase());
        return this.proxy.getChildrenValue(env, parent, filterExpr, isAllSubLevel);
    }
}

