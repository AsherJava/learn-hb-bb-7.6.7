/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model.config;

import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterRangeValueConfig
extends AbstractParameterValueConfig {
    private static final long serialVersionUID = 1L;
    private String defaultMaxValueMode = "none";
    private AbstractParameterValue defaultMaxValue;

    public String getDefaultMaxValueMode() {
        return this.defaultMaxValueMode;
    }

    public void setDefaultMaxValueMode(String defaultMaxValueMode) {
        this.defaultMaxValueMode = defaultMaxValueMode;
    }

    public String getDefaultMinValueMode() {
        return this.getDefaultValueMode();
    }

    public AbstractParameterValue getDefaultMaxValue() {
        return this.defaultMaxValue;
    }

    public void setDefaultMaxValue(AbstractParameterValue defaultMaxValue) {
        this.defaultMaxValue = defaultMaxValue;
    }

    @Override
    public void fromJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        super.fromJson(json, datasource);
        this.defaultMaxValueMode = this.parseValueMode(json, "defaultMaxValueMode", "extDefaultMaxValueMode");
        JSONObject dv_json = json.optJSONObject("defaultMaxValue");
        if (dv_json != null) {
            this.defaultMaxValue = AbstractParameterValue.loadFromJson(dv_json, datasource);
        }
    }

    @Override
    public void toJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        super.toJson(json, datasource);
        json.put("defaultMaxValueMode", (Object)this.defaultMaxValueMode);
        if (this.defaultMaxValue != null) {
            JSONObject defaultValueJson = new JSONObject();
            this.defaultMaxValue.toJson(defaultValueJson, datasource);
            json.put("defaultMaxValue", (Object)defaultValueJson);
        }
    }
}

