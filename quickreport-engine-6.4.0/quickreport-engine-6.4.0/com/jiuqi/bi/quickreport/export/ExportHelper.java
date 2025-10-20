/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.ParameterException
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  com.jiuqi.bi.parameter.model.ParameterWidgetType
 *  com.jiuqi.bi.parameter.model.SmartSelector
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.export;

import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.ParameterWidgetType;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.quickreport.html.message.ParamStringValuesParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

@Deprecated
public class ExportHelper {
    public JSONArray env2json(IParameterEnv env) throws ParameterException {
        JSONArray json_parameterValues = new JSONArray();
        for (ParameterModel parameterModel : env.getParameterModels().toArray(new ParameterModel[0])) {
            new JSONArray((Collection)env.getKeyValueAsString(parameterModel.getName()));
        }
        for (ParameterModel parameterModel : env.getParameterModels()) {
            JSONObject json_parameterValue = new JSONObject();
            String parameterName = parameterModel.getName();
            json_parameterValue.put("name", (Object)parameterName);
            if (parameterModel.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
                Object value = env.getValue(parameterName);
                json_parameterValue.put("values", (Object)new JSONArray(Collections.singletonList(((SmartSelector)value).toJson())));
            } else {
                json_parameterValue.put("values", (Object)new JSONArray((Collection)env.getKeyValueAsString(parameterName)));
            }
            json_parameterValue.put("dimTree", (Object)env.getDimTree(parameterName));
            json_parameterValue.put("orderChecked", env.isOrderValues(parameterName));
            json_parameterValues.put((Object)json_parameterValue);
        }
        return json_parameterValues;
    }

    public void json2env(JSONArray json_parameterValues, IParameterEnv env) throws ParameterException {
        HashMap<String, JSONArray> valuesMap = new HashMap<String, JSONArray>();
        for (int i = 0; i < json_parameterValues.length(); ++i) {
            JSONObject json_parameterValue = json_parameterValues.getJSONObject(i);
            String parameterName = json_parameterValue.optString("name");
            JSONArray json_values = json_parameterValue.optJSONArray("values");
            valuesMap.put(parameterName, json_values);
            String dimTree = json_parameterValue.optString("dimTree");
            env.setDimTree(parameterName, dimTree);
            boolean orderChecked = json_parameterValue.optBoolean("orderChecked");
            env.setOrderValues(parameterName, orderChecked);
        }
        HashMap<String, Object> paramValueMap = new HashMap<String, Object>();
        ParamStringValuesParser stringValuesParser = new ParamStringValuesParser(env);
        for (String parameterName : valuesMap.keySet()) {
            ParameterModel p = env.getParameterModelByName(parameterName);
            if (p == null) continue;
            JSONArray json_values = (JSONArray)valuesMap.get(parameterName);
            if (p.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
                SmartSelector smartSelector = new SmartSelector();
                smartSelector.load(json_values.getJSONObject(0));
                paramValueMap.put(parameterName, smartSelector);
                continue;
            }
            ArrayList<String> paramValues = new ArrayList<String>();
            for (int i = 0; i < json_values.length(); ++i) {
                String value = null;
                if (!json_values.isNull(i)) {
                    value = json_values.getString(i);
                }
                paramValues.add(value);
            }
            stringValuesParser.parse2Map(parameterName, paramValues, paramValueMap);
        }
        env.initParameterValue(paramValueMap, true);
    }
}

