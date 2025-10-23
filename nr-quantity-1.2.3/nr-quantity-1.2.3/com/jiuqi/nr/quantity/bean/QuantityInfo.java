/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.quantity.bean;

import org.json.JSONObject;

public class QuantityInfo {
    private String id;
    private String name;
    private String title;
    private String order;
    private long modifyTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public long getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", (Object)this.id);
        json.put("name", (Object)this.name);
        json.put("title", (Object)this.title);
        json.put("order", (Object)this.order);
        json.put("modifyTime", this.modifyTime);
        return json;
    }

    public void fromJson(JSONObject json) {
        this.id = json.optString("id");
        this.name = json.optString("name");
        this.title = json.optString("title");
        this.order = json.optString("order");
        this.modifyTime = json.optLong("modifyTime");
    }
}

