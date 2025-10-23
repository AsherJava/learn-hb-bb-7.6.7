/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.vo;

import org.json.JSONObject;

public class SummaryConfigItem {
    private String key;
    private int type;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", (Object)this.key);
        jsonObject.put("type", this.type);
        return jsonObject.toString();
    }

    public void fromJson(JSONObject jsonObject) {
        this.key = jsonObject.getString("key");
        this.type = jsonObject.getInt("type");
    }
}

