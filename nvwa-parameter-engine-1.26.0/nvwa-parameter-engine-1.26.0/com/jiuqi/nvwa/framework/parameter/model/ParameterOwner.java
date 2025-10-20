/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ParameterOwner
implements Cloneable {
    private Map<String, String> data = new HashMap<String, String>();

    public ParameterOwner() {
    }

    public ParameterOwner(String ownerType, String ownerName) {
        this.data.put("type", ownerType);
        this.data.put("name", ownerName);
    }

    public void setName(String ownerName) {
        this.data.put("name", ownerName);
    }

    public void setType(String ownerType) {
        this.data.put("type", ownerType);
    }

    public String getName() {
        return this.data.get("name");
    }

    public String getType() {
        return this.data.get("type");
    }

    public void set(String key, String value) {
        this.data.put(key, value);
    }

    public String get(String key) {
        return this.data.get(key);
    }

    public void fromJson(JSONObject json) {
        json.keys().forEachRemaining(c -> this.data.put((String)c, json.getString(c)));
    }

    public void toJson(JSONObject json) {
        this.data.entrySet().forEach(c -> json.put((String)c.getKey(), c.getValue()));
    }

    public ParameterOwner clone() {
        ParameterOwner owner;
        try {
            owner = (ParameterOwner)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        this.data.entrySet().forEach(c -> owner.set((String)c.getKey(), (String)c.getValue()));
        return owner;
    }
}

