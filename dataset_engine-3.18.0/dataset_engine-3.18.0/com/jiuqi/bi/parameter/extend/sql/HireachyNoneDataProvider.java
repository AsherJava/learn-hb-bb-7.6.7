/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.parameter.extend.sql;

import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.extend.sql.AbstractSQLDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class HireachyNoneDataProvider
extends AbstractSQLDataSourceDataProvider {
    HireachyNoneDataProvider() {
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> filterChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env, boolean isFirstLevel) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> allValues;
        DataSourceFilterMode filterMode = parameterModel.getDataSourceFilterMode();
        if (filterMode == DataSourceFilterMode.ALL || env.getQueryProperty("initAllValues") != null) {
            allValues = this.querySql(env, new HashMap<String, Object>(), null, -1);
        } else {
            List<String> appointKeyValues = this.buildAppointFilterKeyValues(parameterModel.getDataSourceValues());
            allValues = this.querySql(env, appointKeyValues, null, -1);
        }
        return allValues;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> quickGetChoiceValues(ParameterModel parameterModel, ParameterEngineEnv env, int maxRecordSize, boolean isFirstLevel) throws DataSourceException {
        List<String> appointKeyValues = null;
        if (parameterModel.getDataSourceFilterMode() == DataSourceFilterMode.APPOINT) {
            appointKeyValues = this.buildAppointFilterKeyValues(parameterModel.getDataSourceValues());
            if (appointKeyValues == null || appointKeyValues.isEmpty()) {
                return this.buildEmptyDataset(this.isTreeHierarchy());
            }
        } else {
            new ArrayList();
        }
        return this.querySql(env, appointKeyValues, null, maxRecordSize);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getAllValues(String filterExpr, ParameterEngineEnv env) throws DataSourceException {
        return this.querySql(env, new HashMap<String, Object>(), filterExpr, -1);
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> queryFirstDefaultValue(ParameterModel parameterModel, ParameterEngineEnv env) throws DataSourceException {
        MemoryDataSet<ParameterColumnInfo> dataset;
        List<String> appointKeyValues = null;
        if (parameterModel.getDataSourceFilterMode() == DataSourceFilterMode.APPOINT) {
            appointKeyValues = this.buildAppointFilterKeyValues(parameterModel.getDataSourceValues());
        } else {
            new ArrayList();
        }
        if (parameterModel.isOrderReverse()) {
            dataset = this.querySql(env, appointKeyValues, null, -1);
            if (!dataset.isEmpty()) {
                Object[] lastRow = dataset.getBuffer(dataset.size() - 1);
                MemoryDataSet<ParameterColumnInfo> nds = this.buildEmptyDataset(this.isTreeHierarchy());
                try {
                    nds.add(lastRow);
                }
                catch (DataSetException dataSetException) {
                    // empty catch block
                }
                dataset = nds;
            }
        } else {
            dataset = this.querySql(env, appointKeyValues, null, 1);
        }
        return dataset;
    }

    @Override
    public MemoryDataSet<ParameterColumnInfo> getChildrenValue(ParameterEngineEnv env, String parent, String filter, boolean isAllSubLevel) throws DataSourceException {
        throw new DataSourceException("\u975e\u5c42\u6b21\u7ed3\u6784\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u8c03\u7528\u4e0b\u7ea7\u8282\u70b9\u53d6\u6570\u63a5\u53e3");
    }
}

