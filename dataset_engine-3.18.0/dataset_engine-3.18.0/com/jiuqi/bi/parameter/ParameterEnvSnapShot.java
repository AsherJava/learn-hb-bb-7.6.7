/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.engine.ParamCacheInfo;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterEnvSnapShot
implements Cloneable {
    private Map<String, Object> valuesMap = new HashMap<String, Object>();
    private Map<String, ParamCacheInfo> cacheInfoMap = new HashMap<String, ParamCacheInfo>();
    private Map<String, String> paramUnittreeMap = new HashMap<String, String>();
    private List<ParameterModel> models;
    private static final String TAG_PARAMETERNAME = "parameterName";
    private static final String TAG_VALUE = "value";
    private static final String TAG_VALUES = "values";
    private static final String TAG_VALUETYPE = "valueType";
    private static final String TAG_UNITTREE = "unitTree";

    public ParameterEnvSnapShot(Map<String, Object> valuesMap, Map<String, ParamCacheInfo> cacheInfoMap, List<ParameterModel> models) {
        this.valuesMap = valuesMap;
        this.cacheInfoMap = cacheInfoMap;
        this.models = models;
    }

    public ParameterEnvSnapShot() {
    }

    public Map<String, Object> getValueMap() {
        return this.valuesMap;
    }

    public Map<String, ParamCacheInfo> getCacheInfoMap() {
        return this.cacheInfoMap;
    }

    public Map<String, String> getParamUnittreeMap() {
        return this.paramUnittreeMap;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject valuesObj = new JSONObject();
        JSONArray valueArray = new JSONArray();
        if (this.models != null && this.models.size() != 0 && this.valuesMap.size() != 0) {
            for (ParameterModel parameterModel : this.models) {
                Object value = this.valuesMap.get(parameterModel.getName());
                if (value == null) continue;
                JSONObject valueObj = this.value2Json(parameterModel, value);
                valueArray.put((Object)valueObj);
            }
        }
        valuesObj.put(TAG_VALUES, (Object)valueArray);
        JSONArray treeArray = new JSONArray();
        if (!this.paramUnittreeMap.isEmpty()) {
            for (Map.Entry<String, String> entry : this.paramUnittreeMap.entrySet()) {
                JSONObject j = new JSONObject();
                j.put("pname", (Object)entry.getKey());
                j.put("treeId", (Object)entry.getValue());
                treeArray.put((Object)j);
            }
        }
        valuesObj.put(TAG_UNITTREE, (Object)treeArray);
        return valuesObj;
    }

    private JSONObject value2Json(ParameterModel model, Object value) throws JSONException {
        JSONObject valueObj = new JSONObject();
        if (value != null) {
            DataSourceModel dataSourceModel = model.getDataSourceModel();
            if (dataSourceModel == null) {
                DataType dataType = model.getDataType();
                valueObj.put(TAG_PARAMETERNAME, (Object)model.getName());
                valueObj.put(TAG_VALUETYPE, dataType.value());
                valueObj.put(TAG_VALUE, value);
            } else {
                valueObj.put(TAG_PARAMETERNAME, (Object)model.getName());
                valueObj.put(TAG_VALUETYPE, DataType.UNKNOWN.value());
                MemoryDataSet dataSet = (MemoryDataSet)value;
                valueObj.put(TAG_VALUE, (Object)ParameterEnvSnapShot.memoryDataSet2Json((MemoryDataSet<ParameterColumnInfo>)dataSet));
            }
        }
        return valueObj;
    }

    public static JSONObject memoryDataSet2Json(MemoryDataSet<ParameterColumnInfo> dataSet) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray columnArray = new JSONArray();
        Metadata metaData = dataSet.getMetadata();
        List columns = metaData.getColumns();
        for (Column column : columns) {
            JSONObject columnJsObject = new JSONObject();
            columnJsObject.put("name", (Object)column.getName());
            columnJsObject.put("dataType", column.getDataType());
            columnArray.put((Object)columnJsObject);
        }
        jsonObject.put("metaData", (Object)columnArray);
        JSONArray valueArray = new JSONArray();
        for (DataRow row : dataSet) {
            JSONObject valueObject = new JSONObject();
            for (int i = 0; i < columns.size(); ++i) {
                Object value = row.getValue(i);
                valueObject.put("" + i, value);
            }
            valueArray.put((Object)valueObject);
        }
        jsonObject.put(TAG_VALUE, (Object)valueArray);
        return jsonObject;
    }

    public void fromJson(JSONObject valueJson) throws JSONException {
        JSONArray treeArray;
        this.valuesMap.clear();
        JSONArray values = valueJson.getJSONArray(TAG_VALUES);
        if (values.length() != 0) {
            for (int i = 0; i < values.length(); ++i) {
                JSONObject value = values.getJSONObject(i);
                int dataTypeIndex = value.getInt(TAG_VALUETYPE);
                DataType dataType = DataType.valueOf(dataTypeIndex);
                String parameterName = value.getString(TAG_PARAMETERNAME);
                if (dataType.equals((Object)DataType.UNKNOWN)) {
                    JSONObject dataSetJson = value.getJSONObject(TAG_VALUE);
                    MemoryDataSet<ParameterColumnInfo> dataSet = DataSourceUtils.json2MemoryDataSet(dataSetJson);
                    this.valuesMap.put(parameterName, dataSet);
                    continue;
                }
                Object valueObj = value.get(TAG_VALUE);
                this.valuesMap.put(parameterName, valueObj);
            }
        }
        if ((treeArray = valueJson.optJSONArray(TAG_UNITTREE)) != null) {
            for (int i = 0; i < treeArray.length(); ++i) {
                JSONObject j = treeArray.getJSONObject(i);
                this.paramUnittreeMap.put(j.getString("pname"), j.getString("treeId"));
            }
        }
    }

    public ParameterEnvSnapShot clone() {
        try {
            ArrayList<ParameterModel> cloneModels = new ArrayList<ParameterModel>();
            if (this.models != null && this.models.size() != 0) {
                for (ParameterModel parameterModel : this.models) {
                    cloneModels.add(parameterModel.clone());
                }
            }
            HashMap<String, Object> clonedValuesMap = new HashMap<String, Object>();
            if (this.valuesMap != null && this.valuesMap.size() != 0) {
                for (Map.Entry<String, Object> entry : this.valuesMap.entrySet()) {
                    Object object = entry.getValue();
                    if (object instanceof MemoryDataSet) {
                        MemoryDataSet dataSet = (MemoryDataSet)object;
                        clonedValuesMap.put(entry.getKey(), dataSet.clone());
                        continue;
                    }
                    clonedValuesMap.put(entry.getKey(), object);
                }
            }
            HashMap<String, ParamCacheInfo> hashMap = new HashMap<String, ParamCacheInfo>();
            if (this.cacheInfoMap != null && this.cacheInfoMap.size() != 0) {
                for (Map.Entry<String, ParamCacheInfo> entry : this.cacheInfoMap.entrySet()) {
                    ParamCacheInfo value = entry.getValue();
                    hashMap.put(entry.getKey(), value);
                }
            }
            HashMap<String, String> hashMap2 = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : this.paramUnittreeMap.entrySet()) {
                hashMap2.put(entry.getKey(), entry.getValue());
            }
            ParameterEnvSnapShot parameterEnvSnapShot = new ParameterEnvSnapShot(clonedValuesMap, hashMap, cloneModels);
            parameterEnvSnapShot.paramUnittreeMap = hashMap2;
            return parameterEnvSnapShot;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

