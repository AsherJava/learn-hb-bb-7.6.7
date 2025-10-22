/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.dataset;

import org.json.JSONObject;

public class BaseParaExtInfo {
    private static final String TAG_TYPE = "type";
    private static final String TAG_PARANAME = "paraName";
    private static final String TAG_EXT = "ext";
    private String type;
    private String paraName;

    public String getParaName() {
        return this.paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public final void toJSON(JSONObject json) {
        json.put(TAG_TYPE, (Object)this.type);
        json.put(TAG_PARANAME, (Object)this.paraName);
        JSONObject extJsonObject = new JSONObject();
        this.saveExtToJSON(extJsonObject);
        json.put(TAG_EXT, (Object)extJsonObject);
    }

    public final void fromJSON(JSONObject json) {
        this.type = json.getString(TAG_TYPE);
        this.paraName = json.getString(TAG_PARANAME);
        JSONObject extJsonObject = json.getJSONObject(TAG_EXT);
        this.loadExtFromJSON(extJsonObject);
    }

    protected void loadExtFromJSON(JSONObject extJsonObject) {
    }

    protected void saveExtToJSON(JSONObject extJsonObject) {
    }
}

