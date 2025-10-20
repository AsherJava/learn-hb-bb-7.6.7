/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.storage;

import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceTreeNode;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterBean
extends ParameterResourceTreeNode {
    private String name;
    private static final String TAG_NAME = "name";

    @Override
    public void save(JSONObject value) throws JSONException {
        super.save(value);
        value.put(TAG_NAME, (Object)this.name);
    }

    @Override
    public void load(JSONObject value) throws JSONException {
        super.load(value);
        this.name = value.getString(TAG_NAME);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

