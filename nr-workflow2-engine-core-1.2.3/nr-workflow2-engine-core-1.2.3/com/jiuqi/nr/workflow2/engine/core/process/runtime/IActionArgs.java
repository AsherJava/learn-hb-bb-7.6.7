/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IActionArgs {
    public boolean getBoolean(String var1);

    public String getString(String var1);

    public double getDouble(String var1);

    public float getFloat(String var1);

    public int getInt(String var1);

    public long getLong(String var1);

    public JSONArray getJSONArray(String var1);

    public JSONObject getJSONObject(String var1);

    public Object get(String var1);
}

