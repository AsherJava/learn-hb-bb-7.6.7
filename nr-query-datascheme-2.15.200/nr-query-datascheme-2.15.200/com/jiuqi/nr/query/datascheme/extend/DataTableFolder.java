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

public class DataTableFolder {
    private String id;
    private String title;
    private String type;
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TYPE = "type";

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

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_ID, (Object)this.id);
        value.put(TAG_TITLE, (Object)this.title);
        value.put(TAG_TYPE, (Object)this.type);
    }

    public void load(JSONObject value) throws JSONException {
        this.id = value.getString(TAG_ID);
        this.title = value.getString(TAG_TITLE);
        this.type = value.getString(TAG_TYPE);
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException arg1) {
            throw new RuntimeException(arg1);
        }
    }
}

