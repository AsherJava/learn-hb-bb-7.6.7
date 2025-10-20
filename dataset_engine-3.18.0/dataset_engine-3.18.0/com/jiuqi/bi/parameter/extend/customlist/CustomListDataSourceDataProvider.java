/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 */
package com.jiuqi.bi.parameter.extend.customlist;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.customlist.CustomListDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomListDataSourceDataProvider
implements IDataSourceDataProvider {
    private CustomListDataSourceModel dataSourceModel;

    public CustomListDataSourceDataProvider(DataSourceModel dataSourceModel) {
        this.dataSourceModel = (CustomListDataSourceModel)dataSourceModel;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        DataSourceFilterMode dataSourceFilterMode = parameterModel.getDataSourceFilterMode();
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.ALL)) {
            return DataSourceUtils.dataSourceValueModels2ParamterDataSet(this.dataSourceModel.getValues());
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.APPOINT)) {
            Object dataSourceValues = parameterModel.getDataSourceValues();
            List values = (List)dataSourceValues;
            this.fillTitle(parameterModel, values);
            return this.filterValues(values, env);
        }
        if (dataSourceFilterMode.equals((Object)DataSourceFilterMode.EXPRESSION)) {
            String dataSourceFilter = parameterModel.getDataSourceFilter();
            return this.filterValues(dataSourceFilter, env);
        }
        return null;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterDefaultValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        ParameterDefaultValueFilterMode defaultValueFilterMode = parameterModel.getDefaultValueFilterMode();
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.APPOINT)) {
            Object defaultValues = parameterModel.getDefaultValues();
            List values = (List)defaultValues;
            this.fillTitle(parameterModel, values);
            return this.filterValues(values, env);
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.FIRST)) {
            MemoryDataSet<ParameterColumnInfo> dataSet = this.filterChoiceValues(parameterModel, env);
            MemoryDataSet<ParameterColumnInfo> resultSet = DataSourceUtils.getFirstValue(ParameterUtils.reverseDataSet(parameterModel, dataSet));
            return resultSet;
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.EXPRESSION)) {
            String expression = parameterModel.getDefalutValueFilter();
            return this.filterValues(expression, env);
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.NONE)) {
            return DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> filterValues(List<DataSourceValueModel> values, ParameterEngineEnv env) throws DataSourceException {
        try {
            return DataSourceUtils.dataSourceValueModels2ParamterDataSet(values);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    private MemoryDataSet<ParameterColumnInfo> filterValues(String expression, ParameterEngineEnv env) throws DataSourceException {
        return null;
    }

    @Override
    public void init(ParameterEngineEnv env) throws DataSourceException {
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> searchValues(List<String> values, boolean isSearchFromChoiceValues, ParameterEngineEnv env) throws DataSourceException {
        try {
            ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            MemoryDataSet<ParameterColumnInfo> resultSet = DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            if (parameterModel != null) {
                MemoryDataSet<ParameterColumnInfo> choiceValues = isSearchFromChoiceValues ? this.filterChoiceValues(parameterModel, env) : this.getAllValues(null, env);
                if (choiceValues != null && choiceValues.size() != 0) {
                    for (DataRow row : choiceValues) {
                        String key = row.getString(0);
                        String name = row.getString(1);
                        if (!DataSourceUtils.isContainsSearchValues(key, values) && !DataSourceUtils.isContainsSearchValues(name, values)) continue;
                        DataSourceUtils.addDataRow2DataSet(row, resultSet);
                    }
                }
                if (isSearchFromChoiceValues) {
                    return DataSourceUtils.sortByAppointValues(choiceValues, resultSet, this.getDataSourceMetaInfo());
                }
                return resultSet;
            }
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        return DataSourceUtils.dataSourceValueModels2ParamterDataSet(this.dataSourceModel.getValues());
    }

    @Override
    public DataSourceMetaInfo getDataSourceMetaInfo() throws DataSourceException {
        DataSourceMetaInfo metaInfo = new DataSourceMetaInfo();
        DataSourceAttrBean bean = new DataSourceAttrBean();
        bean.setCurrAttrName("code");
        bean.setKeyColName("code");
        bean.setNameColName("name");
        bean.setTitle("\u540d\u79f0;\u7f16\u7801");
        metaInfo.getAttrBeans().add(bean);
        metaInfo.setLevelDepth(0);
        return metaInfo;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterValues(Object values, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> choiceValues = this.filterChoiceValues(parameterModel, env);
        return this.filterValuesAndFillTitle(choiceValues, (MemoryDataSet<ParameterColumnInfo>)((MemoryDataSet)values));
    }

    private MemoryDataSet<ParameterColumnInfo> filterValuesAndFillTitle(MemoryDataSet<ParameterColumnInfo> choiceValues, MemoryDataSet<ParameterColumnInfo> values) {
        MemoryDataSet rs = new MemoryDataSet(ParameterColumnInfo.class);
        rs.getMetadata().addColumn(new Column("code", 6));
        rs.getMetadata().addColumn(new Column("name", 6));
        for (DataRow dataRow : choiceValues) {
            for (DataRow dr : values) {
                if (!dr.getValue(0).equals(dataRow.getValue(0))) continue;
                DataRow ds = rs.add();
                ds.setValue(0, dr.getValue(0));
                ds.setValue(1, dataRow.getValue(1));
            }
        }
        return rs;
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
        ArrayList<DataSourceCandidateFieldInfo> list = new ArrayList<DataSourceCandidateFieldInfo>();
        list.add(new DataSourceCandidateFieldInfo("code", "\u7f16\u7801"));
        list.add(new DataSourceCandidateFieldInfo("title", "\u6807\u9898"));
        return list;
    }

    private void fillTitle(ParameterModel parameterModel, List<DataSourceValueModel> values) {
        if (values != null) {
            CustomListDataSourceModel dataSourceModel = (CustomListDataSourceModel)parameterModel.getDataSourceModel();
            HashMap<String, DataSourceValueModel> map = new HashMap<String, DataSourceValueModel>();
            for (DataSourceValueModel value : dataSourceModel.getValues()) {
                map.put(value.getCode(), value);
            }
            for (int i = values.size() - 1; i > -1; --i) {
                DataSourceValueModel value;
                value = values.get(i);
                if (map.containsKey(value.getCode())) {
                    DataSourceValueModel v = (DataSourceValueModel)map.get(value.getCode());
                    value.setName(v.getName());
                    continue;
                }
                values.remove(i);
            }
        }
    }
}

