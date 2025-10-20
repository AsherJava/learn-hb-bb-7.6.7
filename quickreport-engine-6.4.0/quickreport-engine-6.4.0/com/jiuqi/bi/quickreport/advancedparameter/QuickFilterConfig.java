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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuickFilterConfig
implements Serializable {
    private static final long serialVersionUID = 7080455439969859518L;
    private boolean autoQuery;
    private boolean allParameter;
    private boolean queryByDefault = true;
    private final List<String> paraNames = new ArrayList<String>();
    private static final String TAG_AUTOQUERY = "autoQuery";
    private static final String TAG_ALLPARA = "allPara";
    private static final String TAG_QUERYBYDEFAULT = "queryByDefault";
    private static final String TAG_PARANAMES = "paraNames";

    public boolean isAllParameter() {
        return this.allParameter;
    }

    public void setAllParameter(boolean allParameter) {
        this.allParameter = allParameter;
    }

    public boolean isAutoQuery() {
        return this.autoQuery;
    }

    public void setAutoQuery(boolean autoQuery) {
        this.autoQuery = autoQuery;
    }

    public boolean isQueryByDefault() {
        return this.queryByDefault;
    }

    public void setQueryByDefault(boolean queryByDefault) {
        this.queryByDefault = queryByDefault;
    }

    public List<String> getParaNames() {
        return this.paraNames;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject value = new JSONObject();
        value.put(TAG_AUTOQUERY, this.autoQuery);
        value.put(TAG_ALLPARA, this.allParameter);
        value.put(TAG_QUERYBYDEFAULT, this.queryByDefault);
        JSONArray namesArray = new JSONArray();
        for (String name : this.paraNames) {
            namesArray.put((Object)name);
        }
        value.put(TAG_PARANAMES, (Object)namesArray);
        return value;
    }

    public void fromJson(JSONObject value) throws JSONException {
        this.allParameter = value.optBoolean(TAG_ALLPARA);
        this.autoQuery = value.optBoolean(TAG_AUTOQUERY, false);
        this.queryByDefault = value.optBoolean(TAG_QUERYBYDEFAULT, true);
        JSONArray namesArray = value.optJSONArray(TAG_PARANAMES);
        for (int i = 0; i < namesArray.length(); ++i) {
            this.paraNames.add(namesArray.optString(i));
        }
    }

    @Deprecated
    public void fromJson(JSONObject value, List<ParameterModel> paraModels) throws JSONException {
        this.fromJson(value);
    }
}

