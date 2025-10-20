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
import com.jiuqi.bi.quickreport.advancedparameter.ParameterGroup;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupShowConfig {
    private List<ParameterGroup> groups = new ArrayList<ParameterGroup>();
    private static final String TAG_GROUPS = "groups";

    public List<ParameterGroup> getGroups() {
        return this.groups;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject value = new JSONObject();
        JSONArray array = new JSONArray();
        value.put(TAG_GROUPS, (Object)array);
        for (ParameterGroup group : this.groups) {
            array.put((Object)group.toJson());
        }
        return value;
    }

    public void fromJson(JSONObject value, List<ParameterModel> paraModels) throws JSONException {
        JSONArray array = value.optJSONArray(TAG_GROUPS);
        for (int i = 0; i < array.length(); ++i) {
            JSONObject obj = array.optJSONObject(i);
            ParameterGroup group = new ParameterGroup();
            group.fromJson(obj, paraModels);
            this.groups.add(group);
        }
    }
}

