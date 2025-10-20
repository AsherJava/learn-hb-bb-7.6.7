/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo
 */
package com.jiuqi.bi.parameter.extend.zb;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.ParameterUtils;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.zb.ZbParameterDataSourceModel;
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

public class ZbParameterDataSourceDataProvider
implements IDataSourceDataProvider {
    private ZbParameterDataSourceModel dataSourceModel;

    public ZbParameterDataSourceDataProvider(DataSourceModel dataSourceModel) {
        this.dataSourceModel = (ZbParameterDataSourceModel)dataSourceModel;
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

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterDefaultValues(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        ParameterDefaultValueFilterMode defaultValueFilterMode = parameterModel.getDefaultValueFilterMode();
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.APPOINT)) {
            Object defaultValues = parameterModel.getDefaultValues();
            List values = (List)defaultValues;
            this.fillTitle(parameterModel, values);
            return this.filterValuesForList(values, env);
        }
        if (defaultValueFilterMode.equals((Object)ParameterDefaultValueFilterMode.FIRST)) {
            List<DataSourceValueModel> values = this.dataSourceModel.getValues();
            ArrayList<DataSourceValueModel> resultValues = new ArrayList<DataSourceValueModel>();
            if (!values.isEmpty()) {
                resultValues.add(values.get(0));
            }
            return DataSourceUtils.dataSourceValueModels2ParamterDataSet(resultValues);
        }
        return null;
    }

    private MemoryDataSet<ParameterColumnInfo> filterValuesForList(List<DataSourceValueModel> values, ParameterEngineEnv env) throws DataSourceException {
        try {
            return DataSourceUtils.dataSourceValueModels2ParamterDataSet(values);
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public void init(ParameterEngineEnv env) throws DataSourceException {
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> searchValues(List<String> values, boolean isSearchFromChoiceValues, ParameterEngineEnv env) throws DataSourceException {
        try {
            MemoryDataSet<ParameterColumnInfo> resultDataSet = DataSourceUtils.getMemoryDataSet(this.getDataSourceMetaInfo());
            ParameterModel parameterModel = ParameterUtils.getParameterModel(this.dataSourceModel, env.getParameterModels());
            MemoryDataSet<ParameterColumnInfo> choiceValues = null;
            if (parameterModel != null && (choiceValues = isSearchFromChoiceValues ? this.filterChoiceValues(parameterModel, env) : this.getAllValues(null, env)) != null && choiceValues.size() != 0) {
                for (DataRow row : choiceValues) {
                    String key = row.getString(0);
                    String name = row.getString(1);
                    if (!DataSourceUtils.isContainsSearchValues(key, values) && !DataSourceUtils.isContainsSearchValues(name, values)) continue;
                    DataSourceUtils.addDataRow2DataSet(row, resultDataSet);
                }
            }
            if (isSearchFromChoiceValues) {
                return DataSourceUtils.sortByAppointValues(choiceValues, resultDataSet, this.getDataSourceMetaInfo());
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException(e.getMessage(), e);
        }
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        return DataSourceUtils.dataSourceValueModels2ParamterDataSet(this.dataSourceModel.getValues());
    }

    @Override
    public DataSourceMetaInfo getDataSourceMetaInfo() throws DataSourceException {
        DataSourceMetaInfo metaInfo = new DataSourceMetaInfo();
        DataSourceAttrBean attrBean = new DataSourceAttrBean();
        attrBean.setCurrAttrName("code");
        attrBean.setKeyColName("code");
        attrBean.setNameColName("name");
        attrBean.setTitle("\u540d\u79f0;\u7f16\u7801");
        metaInfo.getAttrBeans().add(attrBean);
        return metaInfo;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterValues(Object values, ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        HashMap<String, String> valueMap = new HashMap<String, String>();
        ZbParameterDataSourceModel zbParameterDataSourceModel = (ZbParameterDataSourceModel)parameterModel.getDataSourceModel();
        for (DataSourceValueModel valueModel : zbParameterDataSourceModel.getValues()) {
            if (valueMap.containsKey(valueModel.getCode())) continue;
            valueMap.put(valueModel.getCode(), valueModel.getName());
        }
        MemoryDataSet dataSet = (MemoryDataSet)values;
        for (int r = 0; r < dataSet.size(); ++r) {
            String code = dataSet.get(r).getString(0);
            if (!valueMap.containsKey(code)) continue;
            dataSet.get(r).setString(1, (String)valueMap.get(code));
        }
        return (MemoryDataSet)values;
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
        list.add(new DataSourceCandidateFieldInfo("name", "\u6807\u9898"));
        return list;
    }

    private void fillTitle(ParameterModel parameterModel, List<DataSourceValueModel> values) {
        if (values != null) {
            ZbParameterDataSourceModel dataSourceModel = (ZbParameterDataSourceModel)parameterModel.getDataSourceModel();
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

