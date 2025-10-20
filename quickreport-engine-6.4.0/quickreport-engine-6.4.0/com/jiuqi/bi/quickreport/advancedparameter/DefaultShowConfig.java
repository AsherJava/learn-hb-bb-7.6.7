/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.model.ParameterModel
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.advancedparameter;

import com.jiuqi.bi.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DefaultShowConfig {
    private int columnNum;
    private List<String> paraNames = new ArrayList<String>();
    private List<Integer> paraWidths = new ArrayList<Integer>();
    private static final String TAG_COLUMNNUM = "columnNum";
    private static final String TAG_PARANAMES = "paraNames";
    private static final String TAG_PARAWIDTH = "paraWidth";

    public int getColumnNum() {
        return this.columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public JSONObject toJSon() throws JSONException {
        JSONObject value = new JSONObject();
        JSONArray namesArray = new JSONArray();
        for (String name : this.paraNames) {
            namesArray.put((Object)name);
        }
        value.put(TAG_PARANAMES, (Object)namesArray);
        value.put(TAG_COLUMNNUM, this.columnNum);
        JSONArray widthArray = new JSONArray();
        for (Integer width : this.paraWidths) {
            widthArray.put((Object)width);
        }
        value.put(TAG_PARAWIDTH, (Object)widthArray);
        return value;
    }

    public void fromJson(JSONObject value, List<ParameterModel> paraModels) throws JSONException {
        int i;
        this.columnNum = value.optInt(TAG_COLUMNNUM);
        JSONArray namesArray = value.optJSONArray(TAG_PARANAMES);
        for (i = 0; i < namesArray.length(); ++i) {
            for (ParameterModel parameterModel : paraModels) {
                if (!parameterModel.getName().equals(namesArray.optString(i)) && !(parameterModel.getName() + ".MIN").equals(namesArray.optString(i)) && !(parameterModel.getName() + ".MAX").equals(namesArray.optString(i))) continue;
                this.paraNames.add(namesArray.optString(i));
            }
        }
        for (i = 0; i < this.paraNames.size(); ++i) {
            for (ParameterModel parameterModel : paraModels) {
                if (!parameterModel.getName().equals(this.paraNames.get(i)) && !(parameterModel.getName() + ".MIN").equals(namesArray.optString(i)) && !(parameterModel.getName() + ".MAX").equals(namesArray.optString(i))) continue;
                Integer paraWidth = parameterModel.getWidth();
                this.paraWidths.add(paraWidth);
            }
        }
    }

    public List<String> getParaNames() {
        return this.paraNames;
    }

    public List<Integer> getParaWidths() {
        return this.paraWidths;
    }
}

