/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomListDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.bi.datasource.customlist";
    private List<FixedMemberItem> items = new ArrayList<FixedMemberItem>();
    private boolean treatAsZB = false;

    @Override
    public String getType() {
        return TYPE;
    }

    public List<FixedMemberItem> getItems() {
        return this.items;
    }

    public boolean isTreatAsZB() {
        return this.treatAsZB;
    }

    public void setTreatAsZB(boolean treatAsZB) {
        this.treatAsZB = treatAsZB;
    }

    @Override
    protected void toExtJson(JSONObject json) throws JSONException {
        super.toExtJson(json);
        JSONArray ja = new JSONArray();
        for (FixedMemberItem item : this.items) {
            JSONObject j = new JSONObject();
            j.put("code", (Object)item.getCode());
            j.put("title", (Object)item.getTitle());
            ja.put((Object)j);
        }
        json.put("values", (Object)ja);
        json.put("treatAsZB", this.treatAsZB);
    }

    @Override
    protected void fromExtJson(JSONObject json) throws JSONException {
        super.fromExtJson(json);
        this.treatAsZB = json.optBoolean("treatAsZB");
        if (json.isNull("values")) {
            return;
        }
        JSONArray ja = json.getJSONArray("values");
        for (int i = 0; i < ja.length(); ++i) {
            JSONObject j = ja.getJSONObject(i);
            FixedMemberItem item = new FixedMemberItem(j.getString("code"), j.getString("title"));
            this.items.add(item);
        }
    }

    @Override
    public AbstractParameterDataSourceModel clone() {
        CustomListDataSourceModel model = (CustomListDataSourceModel)super.clone();
        model.items = new ArrayList<FixedMemberItem>();
        for (FixedMemberItem item : this.items) {
            model.items.add(new FixedMemberItem(item.getCode(), item.getTitle()));
        }
        return model;
    }

    public static class FixedMemberItem {
        private String code;
        private String title;

        public FixedMemberItem(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public String getCode() {
            return this.code;
        }

        public String getTitle() {
            return this.title;
        }
    }
}

