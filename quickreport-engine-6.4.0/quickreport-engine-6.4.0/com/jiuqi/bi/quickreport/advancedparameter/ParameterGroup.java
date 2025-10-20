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

public class ParameterGroup {
    private String title;
    private int columnNum;
    private String desc;
    private List<String> paraNames = new ArrayList<String>();
    private List<Integer> paraWidths = new ArrayList<Integer>();
    private static final String TAG_TITLE = "title";
    private static final String TAG_COLUMNNUM = "colNum";
    private static final String TAG_DESC = "desc";
    private static final String TAG_PARANAMES = "paraNames";
    private static final String TAG_PARAWIDTH = "paraWidth";

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColumnNum() {
        return this.columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getParaNames() {
        return this.paraNames;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject value = new JSONObject();
        value.put(TAG_TITLE, (Object)this.title);
        value.put(TAG_COLUMNNUM, this.columnNum);
        value.put(TAG_DESC, (Object)this.desc);
        JSONArray namesArray = new JSONArray();
        for (String name : this.paraNames) {
            namesArray.put((Object)name);
        }
        value.put(TAG_PARANAMES, (Object)namesArray);
        JSONArray widthArray = new JSONArray();
        for (Integer width : this.paraWidths) {
            widthArray.put((Object)width);
        }
        value.put(TAG_PARAWIDTH, (Object)widthArray);
        return value;
    }

    public void fromJson(JSONObject value, List<ParameterModel> paraModels) throws JSONException {
        int i;
        this.title = value.optString(TAG_TITLE);
        this.columnNum = value.optInt(TAG_COLUMNNUM);
        if (!value.isNull(TAG_DESC)) {
            this.desc = value.optString(TAG_DESC);
        }
        JSONArray namesArray = value.getJSONArray(TAG_PARANAMES);
        for (i = 0; i < namesArray.length(); ++i) {
            for (ParameterModel parameterModel : paraModels) {
                if (!parameterModel.getName().equals(namesArray.optString(i)) && !(parameterModel.getName() + ".MIN").equals(namesArray.optString(i)) && !(parameterModel.getName() + ".MAX").equals(namesArray.optString(i))) continue;
                this.paraNames.add(namesArray.optString(i));
            }
        }
        for (i = 0; i < this.paraNames.size(); ++i) {
            for (ParameterModel parameterModel : paraModels) {
                if (!parameterModel.getName().equals(namesArray.optString(i)) && !(parameterModel.getName() + ".MIN").equals(namesArray.optString(i)) && !(parameterModel.getName() + ".MAX").equals(namesArray.optString(i))) continue;
                Integer paraWidth = parameterModel.getWidth();
                this.paraWidths.add(paraWidth);
            }
        }
    }

    public List<Integer> getParaWidths() {
        return this.paraWidths;
    }
}

