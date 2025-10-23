/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.datascheme.extend;

import org.json.JSONException;
import org.json.JSONObject;

public class DataTableNode {
    private String key;
    private String name;
    private String title;
    private String type;
    private static final String TAG_KEY = "key";
    private static final String TAG_NAME = "name";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TYPE = "type";

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_KEY, (Object)this.key);
        value.put(TAG_NAME, (Object)this.name);
        value.put(TAG_TITLE, (Object)this.title);
        value.put(TAG_TYPE, (Object)this.type);
    }

    public void load(JSONObject value) throws JSONException {
        this.key = value.getString(TAG_KEY);
        this.name = value.getString(TAG_NAME);
        this.title = value.getString(TAG_TITLE);
        this.type = value.getString(TAG_TYPE);
    }
}

