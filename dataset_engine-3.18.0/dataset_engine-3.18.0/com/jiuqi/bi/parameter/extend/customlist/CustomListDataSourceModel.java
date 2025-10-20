/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.extend.customlist;

import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomListDataSourceModel
extends DataSourceModel
implements Cloneable {
    public static final String TYPE = "com.jiuqi.bi.datasource.customlist";
    private List<DataSourceValueModel> values = new ArrayList<DataSourceValueModel>();
    private static final String TAG_VALUE = "value";

    public CustomListDataSourceModel() {
        this.setType(TYPE);
    }

    public List<DataSourceValueModel> getValues() {
        return this.values;
    }

    public void setValues(List<DataSourceValueModel> values) {
        this.values = values;
    }

    @Override
    protected void loadExt(JSONObject value) throws JSONException {
        if (!value.isNull(TAG_VALUE)) {
            JSONArray valeuJsArray = value.getJSONArray(TAG_VALUE);
            this.values = DataSourceUtils.jsonArray2DataSourceValueModels(valeuJsArray);
        }
    }

    @Override
    protected void saveExt(JSONObject value) throws JSONException {
        if (this.values.size() != 0) {
            JSONArray valeuJsArray = DataSourceUtils.dataSourceValues2JsonArray(this.values);
            value.put(TAG_VALUE, (Object)valeuJsArray);
        }
    }

    @Override
    public CustomListDataSourceModel clone() {
        try {
            CustomListDataSourceModel dataSourceModel = (CustomListDataSourceModel)super.clone();
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

