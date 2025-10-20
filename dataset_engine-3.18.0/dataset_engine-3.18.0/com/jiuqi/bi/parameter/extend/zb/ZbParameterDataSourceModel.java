/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.extend.zb;

import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZbParameterDataSourceModel
extends DataSourceModel
implements Cloneable {
    public static final String TYPE = "com.jiuqi.bi.datasource.zbparameter";
    private List<DataSourceValueModel> values = new ArrayList<DataSourceValueModel>();
    private String zbGroup;

    public ZbParameterDataSourceModel() {
        this.setType(TYPE);
    }

    public List<DataSourceValueModel> getValues() {
        return this.values;
    }

    public void setValues(List<DataSourceValueModel> values) {
        this.values = values;
    }

    public String getZbGroup() {
        return this.zbGroup;
    }

    public void setZbGroup(String zbGroup) {
        this.zbGroup = zbGroup;
    }

    @Override
    protected void loadExt(JSONObject value) throws JSONException {
        if (!value.isNull("values")) {
            JSONArray array = value.getJSONArray("values");
            for (int i = 0; i < array.length(); ++i) {
                JSONObject valueJsObj = array.getJSONObject(i);
                DataSourceValueModel valueModel = new DataSourceValueModel();
                valueModel.fromJson(valueJsObj);
                this.values.add(valueModel);
            }
        }
    }

    @Override
    protected void saveExt(JSONObject value) throws JSONException {
        if (this.values.size() != 0) {
            JSONArray array = new JSONArray();
            for (DataSourceValueModel tempValue : this.values) {
                JSONObject tempJsValue = new JSONObject();
                tempValue.toJson(tempJsValue);
                array.put((Object)tempJsValue);
            }
            value.put("values", (Object)array);
        }
    }

    @Override
    public ZbParameterDataSourceModel clone() {
        try {
            ZbParameterDataSourceModel dataSourceModel = (ZbParameterDataSourceModel)super.clone();
            ArrayList<DataSourceValueModel> dataSourceValuesModels = new ArrayList<DataSourceValueModel>();
            for (DataSourceValueModel valueModel : this.values) {
                dataSourceValuesModels.add(valueModel.clone());
            }
            dataSourceModel.setValues(dataSourceValuesModels);
            return dataSourceModel;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

