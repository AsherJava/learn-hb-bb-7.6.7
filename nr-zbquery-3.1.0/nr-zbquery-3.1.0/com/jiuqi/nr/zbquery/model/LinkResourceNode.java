/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LinkResourceNode {
    private String id;
    private String title;
    private String type;
    private String extData;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", (Object)this.id);
        json.put("title", (Object)this.title);
        json.put("type", (Object)this.type);
        json.put("extData", (Object)this.extData);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.id = json.optString("id");
        this.title = json.optString("title");
        this.type = json.optString("type");
        this.extData = json.optString("extData");
    }
}

