/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.quantity.bean;

import org.json.JSONObject;

public class QuantityCategory {
    private String id;
    private String quantityId;
    private String name;
    private String title;
    private String order;
    private boolean base;
    private double rate;
    private long modifyTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantityId() {
        return this.quantityId;
    }

    public void setQuantityId(String quantityId) {
        this.quantityId = quantityId;
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

    public boolean isBase() {
        return this.base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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
        json.put("quantityId", (Object)this.quantityId);
        json.put("name", (Object)this.name);
        json.put("title", (Object)this.title);
        json.put("order", (Object)this.order);
        json.put("base", this.base);
        json.put("rate", this.rate);
        json.put("modifyTime", this.modifyTime);
        return json;
    }

    public void fromJson(JSONObject json) {
        this.id = json.optString("id");
        this.quantityId = json.optString("quantityId");
        this.name = json.optString("name");
        this.title = json.optString("title");
        this.order = json.optString("order");
        this.base = json.optBoolean("base");
        this.rate = json.optDouble("rate");
        this.modifyTime = json.optLong("modifyTime");
    }
}

