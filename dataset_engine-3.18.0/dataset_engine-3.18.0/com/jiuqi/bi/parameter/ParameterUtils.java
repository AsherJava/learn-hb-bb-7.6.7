/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParameterUtils {
    public static ParameterModel getParameterModel(DataSourceModel dataSourceModel, List<ParameterModel> parameterModels) {
        if (dataSourceModel == null) {
            return null;
        }
        if (parameterModels == null || parameterModels.size() == 0) {
            return null;
        }
        for (ParameterModel parameterModel : parameterModels) {
            if (!dataSourceModel.equals(parameterModel.getDataSourceModel())) continue;
            return parameterModel;
        }
        return parameterModels.get(0);
    }

    public static ParameterModel getParameterModel(String parameterName, List<ParameterModel> parameterModels) {
        if (StringUtils.isEmpty((String)parameterName) || parameterModels == null || parameterModels.size() == 0) {
            return null;
        }
        for (ParameterModel parameterModel : parameterModels) {
            if (!parameterModel.getName().equals(parameterName)) continue;
            return parameterModel;
        }
        return null;
    }

    public static Map<ParameterModel, Object> getCascadeParameterValueModel(DataSourceModel dataSourceModel, ParameterEngineEnv env) throws ParameterException {
        HashMap<ParameterModel, Object> parameterValueMap = new HashMap<ParameterModel, Object>();
        if (env != null) {
            ParameterModel parameterModel = ParameterUtils.getParameterModel(dataSourceModel, env.getParameterModels());
            List<String> cascadeParameters = env.getCascadeRelation().getCascadeParameters(parameterModel.getName());
            ParameterUtils.buildParameterValueMap(cascadeParameters, env, parameterValueMap);
        }
        return parameterValueMap;
    }

    private static void buildParameterValueMap(List<String> cascadeParameters, ParameterEngineEnv env, Map<ParameterModel, Object> parameterValueMap) throws ParameterException {
        if (cascadeParameters == null || cascadeParameters.size() == 0) {
            return;
        }
        for (String parameterName : cascadeParameters) {
            ParameterModel parameterModel = ParameterUtils.getParameterModel(parameterName, env.getParameterModels());
            if (parameterModel == null) continue;
            Object value = env.getValueForInner(parameterName);
            if (parameterModel.isRangeParameter()) {
                MemoryDataSet ds = (MemoryDataSet)value;
                Iterator itor = ds.iterator();
                int pos = parameterName.lastIndexOf(".");
                String pname = parameterName.substring(0, pos);
                parameterModel = ParameterUtils.getParameterModel(pname, env.getParameterModels());
                Object[] kvalue = parameterValueMap.get(parameterModel);
                if (kvalue == null) {
                    kvalue = new Object[2];
                }
                Object[] data = kvalue;
                if (itor.hasNext()) {
                    DataRow row = (DataRow)itor.next();
                    if (parameterName.endsWith(".MAX")) {
                        data[1] = row.getValue(0);
                    } else {
                        data[0] = row.getValue(0);
                    }
                }
                parameterValueMap.put(parameterModel, kvalue);
                continue;
            }
            parameterValueMap.put(parameterModel, value);
        }
    }

    public static List<String> parseSearchValues(String searchValue) {
        String[] searchValues;
        ArrayList<String> values = new ArrayList<String>();
        for (String value : searchValues = searchValue.split(" |,|\uff0c|\u3001")) {
            values.add(value);
        }
        return values;
    }

    public static MemoryDataSet<ParameterColumnInfo> reverseDataSet(ParameterModel parameterModel, MemoryDataSet<ParameterColumnInfo> dataSet) throws DataSourceException {
        try {
            if (!parameterModel.isOrderReverse()) {
                return dataSet;
            }
            MemoryDataSet resultDataSet = new MemoryDataSet();
            Metadata resultMetaData = resultDataSet.getMetadata();
            Metadata metaData = dataSet.getMetadata();
            for (Column column : metaData.getColumns()) {
                resultMetaData.addColumn(column);
            }
            for (int i = dataSet.size() - 1; i >= 0; --i) {
                DataRow row = dataSet.get(i);
                DataRow resultRow = resultDataSet.add();
                for (int j = 0; j < resultMetaData.size(); ++j) {
                    resultRow.setValue(j, row.getValue(j));
                }
                resultRow.commit();
            }
            return resultDataSet;
        }
        catch (Exception e) {
            throw new DataSourceException("\u5012\u5e8f\u6570\u636e\u96c6\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public static boolean isAllHidden(List<ParameterModel> models) {
        if (models != null && models.size() != 0) {
            for (ParameterModel p : models) {
                if (p == null || p.isHidden()) continue;
                return false;
            }
        }
        return true;
    }

    public static List<ParameterModel> getNewParameterList(List<ParameterModel> parameterModels) {
        ArrayList<ParameterModel> newParameterModels = new ArrayList<ParameterModel>();
        if (parameterModels != null && parameterModels.size() != 0) {
            for (ParameterModel parameterModel : parameterModels) {
                if (parameterModel == null) continue;
                newParameterModels.add(parameterModel.clone());
            }
        }
        return newParameterModels;
    }
}

