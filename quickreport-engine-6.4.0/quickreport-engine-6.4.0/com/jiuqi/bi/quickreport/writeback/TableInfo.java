/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.writeback;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class TableInfo
implements Serializable {
    private static final long serialVersionUID = 4564698986447312421L;
    private String name;
    private String title;
    private static final String TAG_NAME = "name";
    private static final String TAG_TITLE = "title";

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

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_NAME, (Object)this.name);
        value.put(TAG_TITLE, (Object)this.title);
    }

    public void load(JSONObject value) throws JSONException {
        this.name = value.getString(TAG_NAME);
        this.title = value.getString(TAG_TITLE);
    }
}

